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
public class CompactionStats implements TBase<CompactionStats, CompactionStats._Fields>, java.io.Serializable, Cloneable, Comparable<CompactionStats> {
  private static final TStruct STRUCT_DESC = new TStruct("CompactionStats");

  private static final TField STATS_FIELD_DESC = new TField("stats", TType.STRUCT, (short)1);
  private static final TField QUEUE_TIME_FIELD_DESC = new TField("queueTime", TType.DOUBLE, (short)2);
  private static final TField QUEUE_SUM_DEV_FIELD_DESC = new TField("queueSumDev", TType.DOUBLE, (short)3);

  public ActionStats stats;
  public double queueTime;
  public double queueSumDev;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    STATS((short)1, "stats"),
    QUEUE_TIME((short)2, "queueTime"),
    QUEUE_SUM_DEV((short)3, "queueSumDev");

    private static final java.util.Map<Integer, _Fields> byId = new java.util.HashMap<Integer, _Fields>();
    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byId.put((int)field._thriftId, field);
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      return byId.get(fieldId);
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
  private static final int __QUEUETIME_ISSET_ID = 0;
  private static final int __QUEUESUMDEV_ISSET_ID = 1;
  private java.util.BitSet __isset_bit_vector = new java.util.BitSet(2);

  public static final java.util.Map<_Fields, FieldMetaData> metaDataMap = java.util.Collections.unmodifiableMap(new java.util.EnumMap<_Fields, FieldMetaData>(_Fields.class) {{
    put(_Fields.STATS, new FieldMetaData("stats", TFieldRequirementType.DEFAULT, 
        new StructMetaData(TType.STRUCT, ActionStats.class)));
    put(_Fields.QUEUE_TIME, new FieldMetaData("queueTime", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.DOUBLE)));
    put(_Fields.QUEUE_SUM_DEV, new FieldMetaData("queueSumDev", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.DOUBLE)));
  }});

  static {
    FieldMetaData.addStructMetaDataMap(CompactionStats.class, metaDataMap);
  }

  public CompactionStats() {
  }

  public CompactionStats(
    ActionStats stats,
    double queueTime,
    double queueSumDev)
  {
    this();
    this.stats = stats;
    this.queueTime = queueTime;
    setQueueTimeIsSet(true);
    this.queueSumDev = queueSumDev;
    setQueueSumDevIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CompactionStats(CompactionStats other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetStats()) {
      this.stats = new ActionStats(other.stats);
    }
    this.queueTime = other.queueTime;
    this.queueSumDev = other.queueSumDev;
  }

  public CompactionStats deepCopy() {
    return new CompactionStats(this);
  }

  @Deprecated
  public CompactionStats clone() {
    return new CompactionStats(this);
  }

  public ActionStats getStats() {
    return this.stats;
  }

  public CompactionStats setStats(ActionStats stats) {
    this.stats = stats;
    return this;
  }

  public void unsetStats() {
    this.stats = null;
  }

  /** Returns true if field stats is set (has been asigned a value) and false otherwise */
  public boolean isSetStats() {
    return this.stats != null;
  }

  public void setStatsIsSet(boolean value) {
    if (!value) {
      this.stats = null;
    }
  }

  public double getQueueTime() {
    return this.queueTime;
  }

  public CompactionStats setQueueTime(double queueTime) {
    this.queueTime = queueTime;
    setQueueTimeIsSet(true);
    return this;
  }

  public void unsetQueueTime() {
    __isset_bit_vector.clear(__QUEUETIME_ISSET_ID);
  }

  /** Returns true if field queueTime is set (has been asigned a value) and false otherwise */
  public boolean isSetQueueTime() {
    return __isset_bit_vector.get(__QUEUETIME_ISSET_ID);
  }

  public void setQueueTimeIsSet(boolean value) {
    __isset_bit_vector.set(__QUEUETIME_ISSET_ID, value);
  }

  public double getQueueSumDev() {
    return this.queueSumDev;
  }

  public CompactionStats setQueueSumDev(double queueSumDev) {
    this.queueSumDev = queueSumDev;
    setQueueSumDevIsSet(true);
    return this;
  }

  public void unsetQueueSumDev() {
    __isset_bit_vector.clear(__QUEUESUMDEV_ISSET_ID);
  }

  /** Returns true if field queueSumDev is set (has been asigned a value) and false otherwise */
  public boolean isSetQueueSumDev() {
    return __isset_bit_vector.get(__QUEUESUMDEV_ISSET_ID);
  }

  public void setQueueSumDevIsSet(boolean value) {
    __isset_bit_vector.set(__QUEUESUMDEV_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STATS:
      if (value == null) {
        unsetStats();
      } else {
        setStats((ActionStats)value);
      }
      break;

    case QUEUE_TIME:
      if (value == null) {
        unsetQueueTime();
      } else {
        setQueueTime((Double)value);
      }
      break;

    case QUEUE_SUM_DEV:
      if (value == null) {
        unsetQueueSumDev();
      } else {
        setQueueSumDev((Double)value);
      }
      break;

    }
  }

  public void setFieldValue(int fieldID, Object value) {
    setFieldValue(_Fields.findByThriftIdOrThrow(fieldID), value);
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STATS:
      return getStats();

    case QUEUE_TIME:
      return new Double(getQueueTime());

    case QUEUE_SUM_DEV:
      return new Double(getQueueSumDev());

    }
    throw new IllegalStateException();
  }

  public Object getFieldValue(int fieldId) {
    return getFieldValue(_Fields.findByThriftIdOrThrow(fieldId));
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    switch (field) {
    case STATS:
      return isSetStats();
    case QUEUE_TIME:
      return isSetQueueTime();
    case QUEUE_SUM_DEV:
      return isSetQueueSumDev();
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
    if (that instanceof CompactionStats)
      return this.equals((CompactionStats)that);
    return false;
  }

  public boolean equals(CompactionStats that) {
    if (that == null)
      return false;

    boolean this_present_stats = true && this.isSetStats();
    boolean that_present_stats = true && that.isSetStats();
    if (this_present_stats || that_present_stats) {
      if (!(this_present_stats && that_present_stats))
        return false;
      if (!this.stats.equals(that.stats))
        return false;
    }

    boolean this_present_queueTime = true;
    boolean that_present_queueTime = true;
    if (this_present_queueTime || that_present_queueTime) {
      if (!(this_present_queueTime && that_present_queueTime))
        return false;
      if (this.queueTime != that.queueTime)
        return false;
    }

    boolean this_present_queueSumDev = true;
    boolean that_present_queueSumDev = true;
    if (this_present_queueSumDev || that_present_queueSumDev) {
      if (!(this_present_queueSumDev && that_present_queueSumDev))
        return false;
      if (this.queueSumDev != that.queueSumDev)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(CompactionStats other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    CompactionStats typedOther = (CompactionStats)other;

    lastComparison = Boolean.valueOf(isSetStats()).compareTo(isSetStats());
    if (lastComparison != 0) {
      return lastComparison;
    }
    lastComparison = TBaseHelper.compareTo(stats, typedOther.stats);
    if (lastComparison != 0) {
      return lastComparison;
    }
    lastComparison = Boolean.valueOf(isSetQueueTime()).compareTo(isSetQueueTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    lastComparison = TBaseHelper.compareTo(queueTime, typedOther.queueTime);
    if (lastComparison != 0) {
      return lastComparison;
    }
    lastComparison = Boolean.valueOf(isSetQueueSumDev()).compareTo(isSetQueueSumDev());
    if (lastComparison != 0) {
      return lastComparison;
    }
    lastComparison = TBaseHelper.compareTo(queueSumDev, typedOther.queueSumDev);
    if (lastComparison != 0) {
      return lastComparison;
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
      _Fields fieldId = _Fields.findByThriftId(field.id);
      if (fieldId == null) {
        TProtocolUtil.skip(iprot, field.type);
      } else {
        switch (fieldId) {
          case STATS:
            if (field.type == TType.STRUCT) {
              this.stats = new ActionStats();
              this.stats.read(iprot);
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case QUEUE_TIME:
            if (field.type == TType.DOUBLE) {
              this.queueTime = iprot.readDouble();
              setQueueTimeIsSet(true);
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          case QUEUE_SUM_DEV:
            if (field.type == TType.DOUBLE) {
              this.queueSumDev = iprot.readDouble();
              setQueueSumDevIsSet(true);
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
        }
        iprot.readFieldEnd();
      }
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.stats != null) {
      oprot.writeFieldBegin(STATS_FIELD_DESC);
      this.stats.write(oprot);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(QUEUE_TIME_FIELD_DESC);
    oprot.writeDouble(this.queueTime);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(QUEUE_SUM_DEV_FIELD_DESC);
    oprot.writeDouble(this.queueSumDev);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CompactionStats(");
    sb.append("stats:");
    if (this.stats == null) {
      sb.append("null");
    } else {
      sb.append(this.stats);
    }
    sb.append(", ");
    sb.append("queueTime:");
    sb.append(this.queueTime);
    sb.append(", ");
    sb.append("queueSumDev:");
    sb.append(this.queueSumDev);
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}

