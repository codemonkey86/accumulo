/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.accumulo.core.data.thrift;



import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.protocol.*;

@SuppressWarnings("serial")
public class IterInfo implements TBase<IterInfo, IterInfo._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("IterInfo");

  private static final TField PRIORITY_FIELD_DESC = new TField("priority", TType.I32, (short)1);
  private static final TField CLASS_NAME_FIELD_DESC = new TField("className", TType.STRING, (short)2);
  private static final TField ITER_NAME_FIELD_DESC = new TField("iterName", TType.STRING, (short)3);

  public int priority;
  public String className;
  public String iterName;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    PRIORITY((short)1, "priority"),
    CLASS_NAME((short)2, "className"),
    ITER_NAME((short)3, "iterName");

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
        case 1: // PRIORITY
          return PRIORITY;
        case 2: // CLASS_NAME
          return CLASS_NAME;
        case 3: // ITER_NAME
          return ITER_NAME;
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
  private static final int __PRIORITY_ISSET_ID = 0;
  private java.util.BitSet __isset_bit_vector = new java.util.BitSet(1);

  public static final java.util.Map<_Fields, FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRIORITY, new FieldMetaData("priority", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.CLASS_NAME, new FieldMetaData("className", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.ITER_NAME, new FieldMetaData("iterName", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(IterInfo.class, metaDataMap);
  }

  public IterInfo() {
  }

  public IterInfo(
    int priority,
    String className,
    String iterName)
  {
    this();
    this.priority = priority;
    setPriorityIsSet(true);
    this.className = className;
    this.iterName = iterName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IterInfo(IterInfo other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.priority = other.priority;
    if (other.isSetClassName()) {
      this.className = other.className;
    }
    if (other.isSetIterName()) {
      this.iterName = other.iterName;
    }
  }

  public IterInfo deepCopy() {
    return new IterInfo(this);
  }

  @Deprecated
  public IterInfo clone() {
    return new IterInfo(this);
  }

  public int getPriority() {
    return this.priority;
  }

  public IterInfo setPriority(int priority) {
    this.priority = priority;
    setPriorityIsSet(true);
    return this;
  }

  public void unsetPriority() {
    __isset_bit_vector.clear(__PRIORITY_ISSET_ID);
  }

  /** Returns true if field priority is set (has been asigned a value) and false otherwise */
  public boolean isSetPriority() {
    return __isset_bit_vector.get(__PRIORITY_ISSET_ID);
  }

  public void setPriorityIsSet(boolean value) {
    __isset_bit_vector.set(__PRIORITY_ISSET_ID, value);
  }

  public String getClassName() {
    return this.className;
  }

  public IterInfo setClassName(String className) {
    this.className = className;
    return this;
  }

  public void unsetClassName() {
    this.className = null;
  }

  /** Returns true if field className is set (has been asigned a value) and false otherwise */
  public boolean isSetClassName() {
    return this.className != null;
  }

  public void setClassNameIsSet(boolean value) {
    if (!value) {
      this.className = null;
    }
  }

  public String getIterName() {
    return this.iterName;
  }

  public IterInfo setIterName(String iterName) {
    this.iterName = iterName;
    return this;
  }

  public void unsetIterName() {
    this.iterName = null;
  }

  /** Returns true if field iterName is set (has been asigned a value) and false otherwise */
  public boolean isSetIterName() {
    return this.iterName != null;
  }

  public void setIterNameIsSet(boolean value) {
    if (!value) {
      this.iterName = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRIORITY:
      if (value == null) {
        unsetPriority();
      } else {
        setPriority((Integer)value);
      }
      break;

    case CLASS_NAME:
      if (value == null) {
        unsetClassName();
      } else {
        setClassName((String)value);
      }
      break;

    case ITER_NAME:
      if (value == null) {
        unsetIterName();
      } else {
        setIterName((String)value);
      }
      break;

    }
  }

  public void setFieldValue(int fieldID, Object value) {
    setFieldValue(_Fields.findByThriftIdOrThrow(fieldID), value);
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRIORITY:
      return new Integer(getPriority());

    case CLASS_NAME:
      return getClassName();

    case ITER_NAME:
      return getIterName();

    }
    throw new IllegalStateException();
  }

  public Object getFieldValue(int fieldId) {
    return getFieldValue(_Fields.findByThriftIdOrThrow(fieldId));
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    switch (field) {
    case PRIORITY:
      return isSetPriority();
    case CLASS_NAME:
      return isSetClassName();
    case ITER_NAME:
      return isSetIterName();
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
    if (that instanceof IterInfo)
      return this.equals((IterInfo)that);
    return false;
  }

  public boolean equals(IterInfo that) {
    if (that == null)
      return false;

    boolean this_present_priority = true;
    boolean that_present_priority = true;
    if (this_present_priority || that_present_priority) {
      if (!(this_present_priority && that_present_priority))
        return false;
      if (this.priority != that.priority)
        return false;
    }

    boolean this_present_className = true && this.isSetClassName();
    boolean that_present_className = true && that.isSetClassName();
    if (this_present_className || that_present_className) {
      if (!(this_present_className && that_present_className))
        return false;
      if (!this.className.equals(that.className))
        return false;
    }

    boolean this_present_iterName = true && this.isSetIterName();
    boolean that_present_iterName = true && that.isSetIterName();
    if (this_present_iterName || that_present_iterName) {
      if (!(this_present_iterName && that_present_iterName))
        return false;
      if (!this.iterName.equals(that.iterName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(IterInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    IterInfo typedOther = (IterInfo)other;

    lastComparison = Boolean.valueOf(isSetPriority()).compareTo(typedOther.isSetPriority());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPriority()) {      lastComparison = TBaseHelper.compareTo(this.priority, typedOther.priority);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetClassName()).compareTo(typedOther.isSetClassName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClassName()) {      lastComparison = TBaseHelper.compareTo(this.className, typedOther.className);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIterName()).compareTo(typedOther.isSetIterName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIterName()) {      lastComparison = TBaseHelper.compareTo(this.iterName, typedOther.iterName);
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
        case 1: // PRIORITY
          if (field.type == TType.I32) {
            this.priority = iprot.readI32();
            setPriorityIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // CLASS_NAME
          if (field.type == TType.STRING) {
            this.className = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // ITER_NAME
          if (field.type == TType.STRING) {
            this.iterName = iprot.readString();
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
    oprot.writeFieldBegin(PRIORITY_FIELD_DESC);
    oprot.writeI32(this.priority);
    oprot.writeFieldEnd();
    if (this.className != null) {
      oprot.writeFieldBegin(CLASS_NAME_FIELD_DESC);
      oprot.writeString(this.className);
      oprot.writeFieldEnd();
    }
    if (this.iterName != null) {
      oprot.writeFieldBegin(ITER_NAME_FIELD_DESC);
      oprot.writeString(this.iterName);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("IterInfo(");
    sb.append("priority:");
    sb.append(this.priority);
    sb.append(", ");
    sb.append("className:");
    if (this.className == null) {
      sb.append("null");
    } else {
      sb.append(this.className);
    }
    sb.append(", ");
    sb.append("iterName:");
    if (this.iterName == null) {
      sb.append("null");
    } else {
      sb.append(this.iterName);
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}

