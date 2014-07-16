//
//  CrashEventCollector.m
//  ContextAware
//
//  Created by Slim Besbes on 17/01/13.
//  Copyright (c) 2013 Motwin. All rights reserved.
//

#import "CrashEventCollector.h"
#include <libkern/OSAtomic.h>
#include <execinfo.h>

NSString * const SignalExceptionNameValue   = @"UncaughtExceptionHandlerSignalExceptionName";
NSString * const HandlerSignalKeyValue      = @"UncaughtExceptionHandlerSignalKey";
NSString * const HandlerAddressesKeyValue   = @"UncaughtExceptionHandlerAddressesKey";

const NSInteger SkipAddressCountValue   = 4;
const NSInteger ReportAddressCountValue = 5;

@interface CrashEventCollector()
{
    NSFileManager *fileManager;
    NSString      *documentsDirectory;
    NSString      *crashFilePath;
}

@property(nonatomic, strong) NSFileManager *fileManager;
@property(nonatomic, strong) NSString      *documentsDirectory;
@property(nonatomic, strong) NSString      *crashFilePath;

@end

@implementation CrashEventCollector

@synthesize fileManager;
@synthesize documentsDirectory;
@synthesize crashFilePath;

+ (NSArray *)backtrace
{
    void *callstack[128];
    int frames  = backtrace(callstack, 128);
    char **strs = backtrace_symbols(callstack, frames);
    
    int i;
    NSMutableArray *backtrace = [NSMutableArray arrayWithCapacity:frames];
    
    for (i = SkipAddressCountValue; i < SkipAddressCountValue + ReportAddressCountValue;i++){
	 	[backtrace addObject:[NSString stringWithUTF8String:strs[i]]];
    }
    
    free(strs);
    
    return backtrace;
}

- (void)start
{
    InstallUncaughtExceptionHandler();
    
    NSError  *error;
    
    self.fileManager        = [NSFileManager defaultManager];
    self.documentsDirectory = [NSHomeDirectory() stringByAppendingPathComponent:FOLDER_NAME];
    self.crashFilePath      = [self.documentsDirectory stringByAppendingPathComponent:CRASH_FILE];
    
    BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:self.crashFilePath];
    
    if(fileExists) {
        
        NSString *fileContent = [NSString stringWithContentsOfFile:self.crashFilePath
                                                          encoding:NSUTF8StringEncoding
                                                             error:&error];
        
        NSString *deviceType    = [[UIDevice currentDevice] model];
        NSString *deviceOS      = [[UIDevice currentDevice] systemVersion];
        NSString *appName       = [[[NSBundle mainBundle] infoDictionary] objectForKey:BUNDLE_IDENTIFIER];
        NSString *appVersion    = [[[NSBundle mainBundle] infoDictionary] objectForKey:BUNDLE_VERSION];
        NSString *currentDate   = [[NSDate date] description];
        
        NSString *exceptionMessage = [NSString stringWithFormat:@"DeviceType: %@, DeviceOS: %@ \n AppName: %@, AppVersion:%@ \n Date: %@ \n %@",
                                      deviceType, deviceOS, appName, appVersion, currentDate, fileContent];
        
        ContextElement *contextElement = [[ContextElement alloc] initWithContextType:CRASH_EVENT_KEY
                                                                         contextData:exceptionMessage];
        
        [self onChange:contextElement];
        
        if ([self.fileManager removeItemAtPath:self.crashFilePath error:&error] != YES)
            NSLog(@"Unable to delete file: %@", [error localizedDescription]);
    }
}

- (void)handleException:(NSException *)exception
{
    NSError *error;
    
    self.documentsDirectory = [NSHomeDirectory() stringByAppendingPathComponent:FOLDER_NAME];
    self.crashFilePath      = [self.documentsDirectory stringByAppendingPathComponent:CRASH_FILE];
    
    NSString *crashMessage = [NSString stringWithFormat:@"%@, %@",
                              [exception reason],
                              [[exception userInfo] objectForKey:HandlerAddressesKeyValue]];
    
    [crashMessage writeToFile:self.crashFilePath
                   atomically:YES
                     encoding:NSUTF8StringEncoding
                        error:&error];
}

- (void)stop
{
    CFRunLoopRef runLoop = CFRunLoopGetCurrent();
	CFArrayRef allModes  = CFRunLoopCopyAllModes(runLoop);
    
    CFRelease(allModes);
    
	NSSetUncaughtExceptionHandler(NULL);
	signal(SIGABRT, SIG_DFL);
	signal(SIGILL,  SIG_DFL);
	signal(SIGSEGV, SIG_DFL);
	signal(SIGFPE,  SIG_DFL);
	signal(SIGBUS,  SIG_DFL);
	signal(SIGPIPE, SIG_DFL);
    signal(SIGFPE,  SIG_DFL);
    signal(SIGSYS,  SIG_DFL);
}

@end

void HandleException(NSException *exception)
{
	NSArray             *callStack = [CrashEventCollector backtrace];
	NSMutableDictionary *userInfo  = [NSMutableDictionary dictionaryWithDictionary:[exception userInfo]];
    
    [userInfo setObject:callStack forKey:HandlerAddressesKeyValue];
    
    NSException *anException = [NSException exceptionWithName:[exception name]
                                                       reason:[exception reason]
                                                     userInfo:userInfo];
	
	[[[CrashEventCollector alloc] init] performSelectorOnMainThread:@selector(handleException:)
                                                         withObject:anException
                                                      waitUntilDone:YES];
}

void InstallUncaughtExceptionHandler()
{
	NSSetUncaughtExceptionHandler(&HandleException);
	signal(SIGABRT, SIG_IGN);
	signal(SIGILL,  SIG_IGN);
	signal(SIGSEGV, SIG_IGN);
	signal(SIGFPE,  SIG_IGN);
	signal(SIGBUS,  SIG_IGN);
	signal(SIGPIPE, SIG_IGN);
    signal(SIGFPE,  SIG_IGN);
    signal(SIGSYS,  SIG_IGN);
}

