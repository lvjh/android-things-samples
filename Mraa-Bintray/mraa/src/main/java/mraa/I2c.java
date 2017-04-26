/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.10
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package mraa;

public class I2c {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected I2c(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(I2c obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        mraaJNI.delete_I2c(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public I2c(int bus, boolean raw) {
    this(mraaJNI.new_I2c__SWIG_0(bus, raw), true);
  }

  public I2c(int bus) {
    this(mraaJNI.new_I2c__SWIG_1(bus), true);
  }

  public Result frequency(I2cMode mode) {
    return Result.swigToEnum(mraaJNI.I2c_frequency(swigCPtr, this, mode.swigValue()));
  }

  public Result address(short address) {
    return Result.swigToEnum(mraaJNI.I2c_address(swigCPtr, this, address));
  }

  public short readByte() {
    return mraaJNI.I2c_readByte(swigCPtr, this);
  }

  public int read(byte[] data) {
    return mraaJNI.I2c_read(swigCPtr, this, data);
  }

  public short readReg(short reg) {
    return mraaJNI.I2c_readReg(swigCPtr, this, reg);
  }

  public int readWordReg(short reg) {
    return mraaJNI.I2c_readWordReg(swigCPtr, this, reg);
  }

  public int readBytesReg(short reg, byte[] data) {
    return mraaJNI.I2c_readBytesReg(swigCPtr, this, reg, data);
  }

  public Result writeByte(short data) {
    return Result.swigToEnum(mraaJNI.I2c_writeByte(swigCPtr, this, data));
  }

  public Result write(byte[] data) {
    return Result.swigToEnum(mraaJNI.I2c_write(swigCPtr, this, data));
  }

  public Result writeReg(short reg, short data) {
    return Result.swigToEnum(mraaJNI.I2c_writeReg(swigCPtr, this, reg, data));
  }

  public Result writeWordReg(short reg, int data) {
    return Result.swigToEnum(mraaJNI.I2c_writeWordReg(swigCPtr, this, reg, data));
  }

}