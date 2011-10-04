/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.accumulo.core.tabletserver.thrift;



import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.protocol.*;

@SuppressWarnings("serial")
public class NotServingTabletException extends Exception implements TBase<NotServingTabletException, NotServingTabletException._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("NotServingTabletException");

  private static final TField EXTENT_FIELD_DESC = new TField("extent", TType.STRUCT, (short)1);

  public org.apache.accumulo.core.data.thrift.TKeyExtent extent;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    EXTENT((short)1, "extent");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // EXTENT
          return EXTENT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments

  public static final java.util.Map<_Fields, FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EXTENT, new FieldMetaData("extent", TFieldRequirementType.DEFAULT, 
        new StructMetaData(TType.STRUCT, org.apache.accumulo.core.data.thrift.TKeyExtent.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(NotServingTabletException.class, metaDataMap);
  }

  public NotServingTabletException() {
  }

  public NotServingTabletException(
    org.apache.accumulo.core.data.thrift.TKeyExtent extent)
  {
    this();
    this.extent = extent;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public NotServingTabletException(NotServingTabletException other) {
    if (other.isSetExtent()) {
      this.extent = new org.apache.accumulo.core.data.thrift.TKeyExtent(other.extent);
    }
  }

  public NotServingTabletException deepCopy() {
    return new NotServingTabletException(this);
  }

  @Deprecated
  public NotServingTabletException clone() {
    return new NotServingTabletException(this);
  }

  public org.apache.accumulo.core.data.thrift.TKeyExtent getExtent() {
    return this.extent;
  }

  public NotServingTabletException setExtent(org.apache.accumulo.core.data.thrift.TKeyExtent extent) {
    this.extent = extent;
    return this;
  }

  public void unsetExtent() {
    this.extent = null;
  }

  /** Returns true if field extent is set (has been asigned a value) and false otherwise */
  public boolean isSetExtent() {
    return this.extent != null;
  }

  public void setExtentIsSet(boolean value) {
    if (!value) {
      this.extent = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case EXTENT:
      if (value == null) {
        unsetExtent();
      } else {
        setExtent((org.apache.accumulo.core.data.thrift.TKeyExtent)value);
      }
      break;

    }
  }

  public void setFieldValue(int fieldID, Object value) {
    setFieldValue(_Fields.findByThriftIdOrThrow(fieldID), value);
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case EXTENT:
      return getExtent();

    }
    throw new IllegalStateException();
  }

  public Object getFieldValue(int fieldId) {
    return getFieldValue(_Fields.findByThriftIdOrThrow(fieldId));
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    switch (field) {
    case EXTENT:
      return isSetExtent();
    }
    throw new IllegalStateException();
  }

  public boolean isSet(int fieldID) {
    return isSet(_Fields.findByThriftIdOrThrow(fieldID));
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof NotServingTabletException)
      return this.equals((NotServingTabletException)that);
    return false;
  }

  public boolean equals(NotServingTabletException that) {
    if (that == null)
      return false;

    boolean this_present_extent = true && this.isSetExtent();
    boolean that_present_extent = true && that.isSetExtent();
    if (this_present_extent || that_present_extent) {
      if (!(this_present_extent && that_present_extent))
        return false;
      if (!this.extent.equals(that.extent))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(NotServingTabletException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    NotServingTabletException typedOther = (NotServingTabletException)other;

    lastComparison = Boolean.valueOf(isSetExtent()).compareTo(typedOther.isSetExtent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExtent()) {      lastComparison = TBaseHelper.compareTo(this.extent, typedOther.extent);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // EXTENT
          if (field.type == TType.STRUCT) {
            this.extent = new org.apache.accumulo.core.data.thrift.TKeyExtent();
            this.extent.read(iprot);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.extent != null) {
      oprot.writeFieldBegin(EXTENT_FIELD_DESC);
      this.extent.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("NotServingTabletException(");
    sb.append("extent:");
    if (this.extent == null) {
      sb.append("null");
    } else {
      sb.append(this.extent);
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}

