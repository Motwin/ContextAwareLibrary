/**
 * 
 */
package com.motwin.android.context.model;

/**
 * Accelerometer Data. <br>
 * Such data is composed of 3 ints for x, y, and z coordinates.
 * 
 */
public class AccelerometerData {
    private float x;
    private float y;
    private float z;

    /**
     * Constructor
     * 
     * @param aX
     *            x
     * @param aY
     *            y
     * @param aZ
     *            z
     */
    public AccelerometerData(float aX, float aY, float aZ) {
        x = aX;
        y = aY;
        z = aZ;
    }

    /**
     * Get x
     * 
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Set x
     * 
     * @param aX
     *            x
     */
    public void setX(float aX) {
        x = aX;
    }

    /**
     * Get y
     * 
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * Set y
     * 
     * @param aY
     *            y
     */
    public void setY(float aY) {
        y = aY;
    }

    /**
     * Get z
     * 
     * @return z
     */
    public float getZ() {
        return z;
    }

    /**
     * Set z
     * 
     * @param aZ
     *            z
     */
    public void setZ(float aZ) {
        z = aZ;
    }

    @Override
    public String toString() {
        return "AccelerometerData [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccelerometerData other = (AccelerometerData) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

}
