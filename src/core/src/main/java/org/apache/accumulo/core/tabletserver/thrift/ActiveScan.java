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
public class ActiveScan implements TBase<ActiveScan, ActiveScan._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ActiveScan");

  private static final TField CLIENT_FIELD_DESC = new TField("client", TType.STRING, (short)2);
  private static final TField USER_FIELD_DESC = new TField("user", TType.STRING, (short)3);
  private static final TField TABLE_ID_FIELD_DESC = new TField("tableId", TType.STRING, (short)4);
  private static final TField AGE_FIELD_DESC = new TField("age", TType.I64, (short)5);
  private static final TField IDLE_TIME_FIELD_DESC = new TField("idleTime", TType.I64, (short)6);
  private static final TField TYPE_FIELD_DESC = new TField("type", TType.I32, (short)7);
  private static final TField STATE_FIELD_DESC = new TField("state", TType.I32, (short)8);
  private static final TField EXTENT_FIELD_DESC = new TField("extent", TType.STRUCT, (short)9);
  private static final TField COLUMNS_FIELD_DESC = new TField("columns", TType.LIST, (short)10);
  private static final TField SSI_LIST_FIELD_DESC = new TField("ssiList", TType.LIST, (short)11);
  private static final TField SSIO_FIELD_DESC = new TField("ssio", TType.MAP, (short)12);

  public String client;
  public String user;
  public String tableId;
  public long age;
  public long idleTime;
  /**
   * 
   * @see ScanType
   */
  public ScanType type;
  /**
   * 
   * @see ScanState
   */
  public ScanState state;
  public org.apache.accumulo.core.data.thrift.TKeyExtent extent;
  public java.util.List<org.apache.accumulo.core.data.thrift.TColumn> columns;
  public java.util.List<org.apache.accumulo.core.data.thrift.IterInfo> ssiList;
  public java.util.Map<String,java.util.Map<String,String>> ssio;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    CLIENT((short)2, "client"),
    USER((short)3, "user"),
    TABLE_ID((short)4, "tableId"),
    AGE((short)5, "age"),
    IDLE_TIME((short)6, "idleTime"),
    /**
     * 
     * @see ScanType
     */
    TYPE((short)7, "type"),
    /**
     * 
     * @see ScanState
     */
    STATE((short)8, "state"),
    EXTENT((short)9, "extent"),
    COLUMNS((short)10, "columns"),
    SSI_LIST((short)11, "ssiList"),
    SSIO((short)12, "ssio");

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
        case 2: // CLIENT
          return CLIENT;
        case 3: // USER
          return USER;
        case 4: // TABLE_ID
          return TABLE_ID;
        case 5: // AGE
          return AGE;
        case 6: // IDLE_TIME
          return IDLE_TIME;
        case 7: // TYPE
          return TYPE;
        case 8: // STATE
          return STATE;
        case 9: // EXTENT
          return EXTENT;
        case 10: // COLUMNS
          return COLUMNS;
        case 11: // SSI_LIST
          return SSI_LIST;
        case 12: // SSIO
          return SSIO;
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
  private static final int __AGE_ISSET_ID = 0;
  private static final int __IDLETIME_ISSET_ID = 1;
  private java.util.BitSet __isset_bit_vector = new java.util.BitSet(2);

  public static final java.util.Map<_Fields, FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLIENT, new FieldMetaData("client", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.USER, new FieldMetaData("user", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.TABLE_ID, new FieldMetaData("tableId", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.AGE, new FieldMetaData("age", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I64)));
    tmpMap.put(_Fields.IDLE_TIME, new FieldMetaData("idleTime", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I64)));
    tmpMap.put(_Fields.TYPE, new FieldMetaData("type", TFieldRequirementType.DEFAULT, 
        new EnumMetaData(TType.ENUM, ScanType.class)));
    tmpMap.put(_Fields.STATE, new FieldMetaData("state", TFieldRequirementType.DEFAULT, 
        new EnumMetaData(TType.ENUM, ScanState.class)));
    tmpMap.put(_Fields.EXTENT, new FieldMetaData("extent", TFieldRequirementType.DEFAULT, 
        new StructMetaData(TType.STRUCT, org.apache.accumulo.core.data.thrift.TKeyExtent.class)));
    tmpMap.put(_Fields.COLUMNS, new FieldMetaData("columns", TFieldRequirementType.DEFAULT, 
        new ListMetaData(TType.LIST, 
            new StructMetaData(TType.STRUCT, org.apache.accumulo.core.data.thrift.TColumn.class))));
    tmpMap.put(_Fields.SSI_LIST, new FieldMetaData("ssiList", TFieldRequirementType.DEFAULT, 
        new ListMetaData(TType.LIST, 
            new StructMetaData(TType.STRUCT, org.apache.accumulo.core.data.thrift.IterInfo.class))));
    tmpMap.put(_Fields.SSIO, new FieldMetaData("ssio", TFieldRequirementType.DEFAULT, 
        new MapMetaData(TType.MAP, 
            new FieldValueMetaData(TType.STRING), 
            new MapMetaData(TType.MAP, 
                new FieldValueMetaData(TType.STRING), 
                new FieldValueMetaData(TType.STRING)))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(ActiveScan.class, metaDataMap);
  }

  public ActiveScan() {
  }

  public ActiveScan(
    String client,
    String user,
    String tableId,
    long age,
    long idleTime,
    ScanType type,
    ScanState state,
    org.apache.accumulo.core.data.thrift.TKeyExtent extent,
    java.util.List<org.apache.accumulo.core.data.thrift.TColumn> columns,
    java.util.List<org.apache.accumulo.core.data.thrift.IterInfo> ssiList,
    java.util.Map<String,java.util.Map<String,String>> ssio)
  {
    this();
    this.client = client;
    this.user = user;
    this.tableId = tableId;
    this.age = age;
    setAgeIsSet(true);
    this.idleTime = idleTime;
    setIdleTimeIsSet(true);
    this.type = type;
    this.state = state;
    this.extent = extent;
    this.columns = columns;
    this.ssiList = ssiList;
    this.ssio = ssio;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ActiveScan(ActiveScan other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetClient()) {
      this.client = other.client;
    }
    if (other.isSetUser()) {
      this.user = other.user;
    }
    if (other.isSetTableId()) {
      this.tableId = other.tableId;
    }
    this.age = other.age;
    this.idleTime = other.idleTime;
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetState()) {
      this.state = other.state;
    }
    if (other.isSetExtent()) {
      this.extent = new org.apache.accumulo.core.data.thrift.TKeyExtent(other.extent);
    }
    if (other.isSetColumns()) {
      java.util.List<org.apache.accumulo.core.data.thrift.TColumn> __this__columns = new java.util.ArrayList<org.apache.accumulo.core.data.thrift.TColumn>();
      for (org.apache.accumulo.core.data.thrift.TColumn other_element : other.columns) {
        __this__columns.add(new org.apache.accumulo.core.data.thrift.TColumn(other_element));
      }
      this.columns = __this__columns;
    }
    if (other.isSetSsiList()) {
      java.util.List<org.apache.accumulo.core.data.thrift.IterInfo> __this__ssiList = new java.util.ArrayList<org.apache.accumulo.core.data.thrift.IterInfo>();
      for (org.apache.accumulo.core.data.thrift.IterInfo other_element : other.ssiList) {
        __this__ssiList.add(new org.apache.accumulo.core.data.thrift.IterInfo(other_element));
      }
      this.ssiList = __this__ssiList;
    }
    if (other.isSetSsio()) {
      java.util.Map<String,java.util.Map<String,String>> __this__ssio = new java.util.HashMap<String,java.util.Map<String,String>>();
      for (java.util.Map.Entry<String, java.util.Map<String,String>> other_element : other.ssio.entrySet()) {

        String other_element_key = other_element.getKey();
        java.util.Map<String,String> other_element_value = other_element.getValue();

        String __this__ssio_copy_key = other_element_key;

        java.util.Map<String,String> __this__ssio_copy_value = new java.util.HashMap<String,String>();
        for (java.util.Map.Entry<String, String> other_element_value_element : other_element_value.entrySet()) {

          String other_element_value_element_key = other_element_value_element.getKey();
          String other_element_value_element_value = other_element_value_element.getValue();

          String __this__ssio_copy_value_copy_key = other_element_value_element_key;

          String __this__ssio_copy_value_copy_value = other_element_value_element_value;

          __this__ssio_copy_value.put(__this__ssio_copy_value_copy_key, __this__ssio_copy_value_copy_value);
        }

        __this__ssio.put(__this__ssio_copy_key, __this__ssio_copy_value);
      }
      this.ssio = __this__ssio;
    }
  }

  public ActiveScan deepCopy() {
    return new ActiveScan(this);
  }

  @Deprecated
  public ActiveScan clone() {
    return new ActiveScan(this);
  }

  public String getClient() {
    return this.client;
  }

  public ActiveScan setClient(String client) {
    this.client = client;
    return this;
  }

  public void unsetClient() {
    this.client = null;
  }

  /** Returns true if field client is set (has been asigned a value) and false otherwise */
  public boolean isSetClient() {
    return this.client != null;
  }

  public void setClientIsSet(boolean value) {
    if (!value) {
      this.client = null;
    }
  }

  public String getUser() {
    return this.user;
  }

  public ActiveScan setUser(String user) {
    this.user = user;
    return this;
  }

  public void unsetUser() {
    this.user = null;
  }

  /** Returns true if field user is set (has been asigned a value) and false otherwise */
  public boolean isSetUser() {
    return this.user != null;
  }

  public void setUserIsSet(boolean value) {
    if (!value) {
      this.user = null;
    }
  }

  public String getTableId() {
    return this.tableId;
  }

  public ActiveScan setTableId(String tableId) {
    this.tableId = tableId;
    return this;
  }

  public void unsetTableId() {
    this.tableId = null;
  }

  /** Returns true if field tableId is set (has been asigned a value) and false otherwise */
  public boolean isSetTableId() {
    return this.tableId != null;
  }

  public void setTableIdIsSet(boolean value) {
    if (!value) {
      this.tableId = null;
    }
  }

  public long getAge() {
    return this.age;
  }

  public ActiveScan setAge(long age) {
    this.age = age;
    setAgeIsSet(true);
    return this;
  }

  public void unsetAge() {
    __isset_bit_vector.clear(__AGE_ISSET_ID);
  }

  /** Returns true if field age is set (has been asigned a value) and false otherwise */
  public boolean isSetAge() {
    return __isset_bit_vector.get(__AGE_ISSET_ID);
  }

  public void setAgeIsSet(boolean value) {
    __isset_bit_vector.set(__AGE_ISSET_ID, value);
  }

  public long getIdleTime() {
    return this.idleTime;
  }

  public ActiveScan setIdleTime(long idleTime) {
    this.idleTime = idleTime;
    setIdleTimeIsSet(true);
    return this;
  }

  public void unsetIdleTime() {
    __isset_bit_vector.clear(__IDLETIME_ISSET_ID);
  }

  /** Returns true if field idleTime is set (has been asigned a value) and false otherwise */
  public boolean isSetIdleTime() {
    return __isset_bit_vector.get(__IDLETIME_ISSET_ID);
  }

  public void setIdleTimeIsSet(boolean value) {
    __isset_bit_vector.set(__IDLETIME_ISSET_ID, value);
  }

  /**
   * 
   * @see ScanType
   */
  public ScanType getType() {
    return this.type;
  }

  /**
   * 
   * @see ScanType
   */
  public ActiveScan setType(ScanType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been asigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  /**
   * 
   * @see ScanState
   */
  public ScanState getState() {
    return this.state;
  }

  /**
   * 
   * @see ScanState
   */
  public ActiveScan setState(ScanState state) {
    this.state = state;
    return this;
  }

  public void unsetState() {
    this.state = null;
  }

  /** Returns true if field state is set (has been asigned a value) and false otherwise */
  public boolean isSetState() {
    return this.state != null;
  }

  public void setStateIsSet(boolean value) {
    if (!value) {
      this.state = null;
    }
  }

  public org.apache.accumulo.core.data.thrift.TKeyExtent getExtent() {
    return this.extent;
  }

  public ActiveScan setExtent(org.apache.accumulo.core.data.thrift.TKeyExtent extent) {
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

  public int getColumnsSize() {
    return (this.columns == null) ? 0 : this.columns.size();
  }

  public java.util.Iterator<org.apache.accumulo.core.data.thrift.TColumn> getColumnsIterator() {
    return (this.columns == null) ? null : this.columns.iterator();
  }

  public void addToColumns(org.apache.accumulo.core.data.thrift.TColumn elem) {
    if (this.columns == null) {
      this.columns = new java.util.ArrayList<org.apache.accumulo.core.data.thrift.TColumn>();
    }
    this.columns.add(elem);
  }

  public java.util.List<org.apache.accumulo.core.data.thrift.TColumn> getColumns() {
    return this.columns;
  }

  public ActiveScan setColumns(java.util.List<org.apache.accumulo.core.data.thrift.TColumn> columns) {
    this.columns = columns;
    return this;
  }

  public void unsetColumns() {
    this.columns = null;
  }

  /** Returns true if field columns is set (has been asigned a value) and false otherwise */
  public boolean isSetColumns() {
    return this.columns != null;
  }

  public void setColumnsIsSet(boolean value) {
    if (!value) {
      this.columns = null;
    }
  }

  public int getSsiListSize() {
    return (this.ssiList == null) ? 0 : this.ssiList.size();
  }

  public java.util.Iterator<org.apache.accumulo.core.data.thrift.IterInfo> getSsiListIterator() {
    return (this.ssiList == null) ? null : this.ssiList.iterator();
  }

  public void addToSsiList(org.apache.accumulo.core.data.thrift.IterInfo elem) {
    if (this.ssiList == null) {
      this.ssiList = new java.util.ArrayList<org.apache.accumulo.core.data.thrift.IterInfo>();
    }
    this.ssiList.add(elem);
  }

  public java.util.List<org.apache.accumulo.core.data.thrift.IterInfo> getSsiList() {
    return this.ssiList;
  }

  public ActiveScan setSsiList(java.util.List<org.apache.accumulo.core.data.thrift.IterInfo> ssiList) {
    this.ssiList = ssiList;
    return this;
  }

  public void unsetSsiList() {
    this.ssiList = null;
  }

  /** Returns true if field ssiList is set (has been asigned a value) and false otherwise */
  public boolean isSetSsiList() {
    return this.ssiList != null;
  }

  public void setSsiListIsSet(boolean value) {
    if (!value) {
      this.ssiList = null;
    }
  }

  public int getSsioSize() {
    return (this.ssio == null) ? 0 : this.ssio.size();
  }

  public void putToSsio(String key, java.util.Map<String,String> val) {
    if (this.ssio == null) {
      this.ssio = new java.util.HashMap<String,java.util.Map<String,String>>();
    }
    this.ssio.put(key, val);
  }

  public java.util.Map<String,java.util.Map<String,String>> getSsio() {
    return this.ssio;
  }

  public ActiveScan setSsio(java.util.Map<String,java.util.Map<String,String>> ssio) {
    this.ssio = ssio;
    return this;
  }

  public void unsetSsio() {
    this.ssio = null;
  }

  /** Returns true if field ssio is set (has been asigned a value) and false otherwise */
  public boolean isSetSsio() {
    return this.ssio != null;
  }

  public void setSsioIsSet(boolean value) {
    if (!value) {
      this.ssio = null;
    }
  }

  @SuppressWarnings("unchecked")
  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CLIENT:
      if (value == null) {
        unsetClient();
      } else {
        setClient((String)value);
      }
      break;

    case USER:
      if (value == null) {
        unsetUser();
      } else {
        setUser((String)value);
      }
      break;

    case TABLE_ID:
      if (value == null) {
        unsetTableId();
      } else {
        setTableId((String)value);
      }
      break;

    case AGE:
      if (value == null) {
        unsetAge();
      } else {
        setAge((Long)value);
      }
      break;

    case IDLE_TIME:
      if (value == null) {
        unsetIdleTime();
      } else {
        setIdleTime((Long)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((ScanType)value);
      }
      break;

    case STATE:
      if (value == null) {
        unsetState();
      } else {
        setState((ScanState)value);
      }
      break;

    case EXTENT:
      if (value == null) {
        unsetExtent();
      } else {
        setExtent((org.apache.accumulo.core.data.thrift.TKeyExtent)value);
      }
      break;

    case COLUMNS:
      if (value == null) {
        unsetColumns();
      } else {
        setColumns((java.util.List<org.apache.accumulo.core.data.thrift.TColumn>)value);
      }
      break;

    case SSI_LIST:
      if (value == null) {
        unsetSsiList();
      } else {
        setSsiList((java.util.List<org.apache.accumulo.core.data.thrift.IterInfo>)value);
      }
      break;

    case SSIO:
      if (value == null) {
        unsetSsio();
      } else {
        setSsio((java.util.Map<String,java.util.Map<String,String>>)value);
      }
      break;

    }
  }

  public void setFieldValue(int fieldID, Object value) {
    setFieldValue(_Fields.findByThriftIdOrThrow(fieldID), value);
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLIENT:
      return getClient();

    case USER:
      return getUser();

    case TABLE_ID:
      return getTableId();

    case AGE:
      return new Long(getAge());

    case IDLE_TIME:
      return new Long(getIdleTime());

    case TYPE:
      return getType();

    case STATE:
      return getState();

    case EXTENT:
      return getExtent();

    case COLUMNS:
      return getColumns();

    case SSI_LIST:
      return getSsiList();

    case SSIO:
      return getSsio();

    }
    throw new IllegalStateException();
  }

  public Object getFieldValue(int fieldId) {
    return getFieldValue(_Fields.findByThriftIdOrThrow(fieldId));
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    switch (field) {
    case CLIENT:
      return isSetClient();
    case USER:
      return isSetUser();
    case TABLE_ID:
      return isSetTableId();
    case AGE:
      return isSetAge();
    case IDLE_TIME:
      return isSetIdleTime();
    case TYPE:
      return isSetType();
    case STATE:
      return isSetState();
    case EXTENT:
      return isSetExtent();
    case COLUMNS:
      return isSetColumns();
    case SSI_LIST:
      return isSetSsiList();
    case SSIO:
      return isSetSsio();
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
    if (that instanceof ActiveScan)
      return this.equals((ActiveScan)that);
    return false;
  }

  public boolean equals(ActiveScan that) {
    if (that == null)
      return false;

    boolean this_present_client = true && this.isSetClient();
    boolean that_present_client = true && that.isSetClient();
    if (this_present_client || that_present_client) {
      if (!(this_present_client && that_present_client))
        return false;
      if (!this.client.equals(that.client))
        return false;
    }

    boolean this_present_user = true && this.isSetUser();
    boolean that_present_user = true && that.isSetUser();
    if (this_present_user || that_present_user) {
      if (!(this_present_user && that_present_user))
        return false;
      if (!this.user.equals(that.user))
        return false;
    }

    boolean this_present_tableId = true && this.isSetTableId();
    boolean that_present_tableId = true && that.isSetTableId();
    if (this_present_tableId || that_present_tableId) {
      if (!(this_present_tableId && that_present_tableId))
        return false;
      if (!this.tableId.equals(that.tableId))
        return false;
    }

    boolean this_present_age = true;
    boolean that_present_age = true;
    if (this_present_age || that_present_age) {
      if (!(this_present_age && that_present_age))
        return false;
      if (this.age != that.age)
        return false;
    }

    boolean this_present_idleTime = true;
    boolean that_present_idleTime = true;
    if (this_present_idleTime || that_present_idleTime) {
      if (!(this_present_idleTime && that_present_idleTime))
        return false;
      if (this.idleTime != that.idleTime)
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_state = true && this.isSetState();
    boolean that_present_state = true && that.isSetState();
    if (this_present_state || that_present_state) {
      if (!(this_present_state && that_present_state))
        return false;
      if (!this.state.equals(that.state))
        return false;
    }

    boolean this_present_extent = true && this.isSetExtent();
    boolean that_present_extent = true && that.isSetExtent();
    if (this_present_extent || that_present_extent) {
      if (!(this_present_extent && that_present_extent))
        return false;
      if (!this.extent.equals(that.extent))
        return false;
    }

    boolean this_present_columns = true && this.isSetColumns();
    boolean that_present_columns = true && that.isSetColumns();
    if (this_present_columns || that_present_columns) {
      if (!(this_present_columns && that_present_columns))
        return false;
      if (!this.columns.equals(that.columns))
        return false;
    }

    boolean this_present_ssiList = true && this.isSetSsiList();
    boolean that_present_ssiList = true && that.isSetSsiList();
    if (this_present_ssiList || that_present_ssiList) {
      if (!(this_present_ssiList && that_present_ssiList))
        return false;
      if (!this.ssiList.equals(that.ssiList))
        return false;
    }

    boolean this_present_ssio = true && this.isSetSsio();
    boolean that_present_ssio = true && that.isSetSsio();
    if (this_present_ssio || that_present_ssio) {
      if (!(this_present_ssio && that_present_ssio))
        return false;
      if (!this.ssio.equals(that.ssio))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(ActiveScan other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ActiveScan typedOther = (ActiveScan)other;

    lastComparison = Boolean.valueOf(isSetClient()).compareTo(typedOther.isSetClient());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClient()) {      lastComparison = TBaseHelper.compareTo(this.client, typedOther.client);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUser()).compareTo(typedOther.isSetUser());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUser()) {      lastComparison = TBaseHelper.compareTo(this.user, typedOther.user);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTableId()).compareTo(typedOther.isSetTableId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTableId()) {      lastComparison = TBaseHelper.compareTo(this.tableId, typedOther.tableId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAge()).compareTo(typedOther.isSetAge());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAge()) {      lastComparison = TBaseHelper.compareTo(this.age, typedOther.age);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIdleTime()).compareTo(typedOther.isSetIdleTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdleTime()) {      lastComparison = TBaseHelper.compareTo(this.idleTime, typedOther.idleTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {      lastComparison = TBaseHelper.compareTo(this.type, typedOther.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetState()).compareTo(typedOther.isSetState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetState()) {      lastComparison = TBaseHelper.compareTo(this.state, typedOther.state);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExtent()).compareTo(typedOther.isSetExtent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExtent()) {      lastComparison = TBaseHelper.compareTo(this.extent, typedOther.extent);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetColumns()).compareTo(typedOther.isSetColumns());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetColumns()) {      lastComparison = TBaseHelper.compareTo(this.columns, typedOther.columns);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSsiList()).compareTo(typedOther.isSetSsiList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSsiList()) {      lastComparison = TBaseHelper.compareTo(this.ssiList, typedOther.ssiList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSsio()).compareTo(typedOther.isSetSsio());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSsio()) {      lastComparison = TBaseHelper.compareTo(this.ssio, typedOther.ssio);
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
        case 2: // CLIENT
          if (field.type == TType.STRING) {
            this.client = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // USER
          if (field.type == TType.STRING) {
            this.user = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // TABLE_ID
          if (field.type == TType.STRING) {
            this.tableId = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // AGE
          if (field.type == TType.I64) {
            this.age = iprot.readI64();
            setAgeIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // IDLE_TIME
          if (field.type == TType.I64) {
            this.idleTime = iprot.readI64();
            setIdleTimeIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // TYPE
          if (field.type == TType.I32) {
            this.type = ScanType.findByValue(iprot.readI32());
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // STATE
          if (field.type == TType.I32) {
            this.state = ScanState.findByValue(iprot.readI32());
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 9: // EXTENT
          if (field.type == TType.STRUCT) {
            this.extent = new org.apache.accumulo.core.data.thrift.TKeyExtent();
            this.extent.read(iprot);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 10: // COLUMNS
          if (field.type == TType.LIST) {
            {
              TList _list4 = iprot.readListBegin();
              this.columns = new java.util.ArrayList<org.apache.accumulo.core.data.thrift.TColumn>(_list4.size);
              for (int _i5 = 0; _i5 < _list4.size; ++_i5)
              {
                org.apache.accumulo.core.data.thrift.TColumn _elem6;
                _elem6 = new org.apache.accumulo.core.data.thrift.TColumn();
                _elem6.read(iprot);
                this.columns.add(_elem6);
              }
              iprot.readListEnd();
            }
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 11: // SSI_LIST
          if (field.type == TType.LIST) {
            {
              TList _list7 = iprot.readListBegin();
              this.ssiList = new java.util.ArrayList<org.apache.accumulo.core.data.thrift.IterInfo>(_list7.size);
              for (int _i8 = 0; _i8 < _list7.size; ++_i8)
              {
                org.apache.accumulo.core.data.thrift.IterInfo _elem9;
                _elem9 = new org.apache.accumulo.core.data.thrift.IterInfo();
                _elem9.read(iprot);
                this.ssiList.add(_elem9);
              }
              iprot.readListEnd();
            }
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 12: // SSIO
          if (field.type == TType.MAP) {
            {
              TMap _map10 = iprot.readMapBegin();
              this.ssio = new java.util.HashMap<String,java.util.Map<String,String>>(2*_map10.size);
              for (int _i11 = 0; _i11 < _map10.size; ++_i11)
              {
                String _key12;
                java.util.Map<String,String> _val13;
                _key12 = iprot.readString();
                {
                  TMap _map14 = iprot.readMapBegin();
                  _val13 = new java.util.HashMap<String,String>(2*_map14.size);
                  for (int _i15 = 0; _i15 < _map14.size; ++_i15)
                  {
                    String _key16;
                    String _val17;
                    _key16 = iprot.readString();
                    _val17 = iprot.readString();
                    _val13.put(_key16, _val17);
                  }
                  iprot.readMapEnd();
                }
                this.ssio.put(_key12, _val13);
              }
              iprot.readMapEnd();
            }
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
    if (this.client != null) {
      oprot.writeFieldBegin(CLIENT_FIELD_DESC);
      oprot.writeString(this.client);
      oprot.writeFieldEnd();
    }
    if (this.user != null) {
      oprot.writeFieldBegin(USER_FIELD_DESC);
      oprot.writeString(this.user);
      oprot.writeFieldEnd();
    }
    if (this.tableId != null) {
      oprot.writeFieldBegin(TABLE_ID_FIELD_DESC);
      oprot.writeString(this.tableId);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(AGE_FIELD_DESC);
    oprot.writeI64(this.age);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(IDLE_TIME_FIELD_DESC);
    oprot.writeI64(this.idleTime);
    oprot.writeFieldEnd();
    if (this.type != null) {
      oprot.writeFieldBegin(TYPE_FIELD_DESC);
      oprot.writeI32(this.type.getValue());
      oprot.writeFieldEnd();
    }
    if (this.state != null) {
      oprot.writeFieldBegin(STATE_FIELD_DESC);
      oprot.writeI32(this.state.getValue());
      oprot.writeFieldEnd();
    }
    if (this.extent != null) {
      oprot.writeFieldBegin(EXTENT_FIELD_DESC);
      this.extent.write(oprot);
      oprot.writeFieldEnd();
    }
    if (this.columns != null) {
      oprot.writeFieldBegin(COLUMNS_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.columns.size()));
        for (org.apache.accumulo.core.data.thrift.TColumn _iter18 : this.columns)
        {
          _iter18.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.ssiList != null) {
      oprot.writeFieldBegin(SSI_LIST_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.ssiList.size()));
        for (org.apache.accumulo.core.data.thrift.IterInfo _iter19 : this.ssiList)
        {
          _iter19.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.ssio != null) {
      oprot.writeFieldBegin(SSIO_FIELD_DESC);
      {
        oprot.writeMapBegin(new TMap(TType.STRING, TType.MAP, this.ssio.size()));
        for (java.util.Map.Entry<String, java.util.Map<String,String>> _iter20 : this.ssio.entrySet())
        {
          oprot.writeString(_iter20.getKey());
          {
            oprot.writeMapBegin(new TMap(TType.STRING, TType.STRING, _iter20.getValue().size()));
            for (java.util.Map.Entry<String, String> _iter21 : _iter20.getValue().entrySet())
            {
              oprot.writeString(_iter21.getKey());
              oprot.writeString(_iter21.getValue());
            }
            oprot.writeMapEnd();
          }
        }
        oprot.writeMapEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ActiveScan(");
    sb.append("client:");
    if (this.client == null) {
      sb.append("null");
    } else {
      sb.append(this.client);
    }
    sb.append(", ");
    sb.append("user:");
    if (this.user == null) {
      sb.append("null");
    } else {
      sb.append(this.user);
    }
    sb.append(", ");
    sb.append("tableId:");
    if (this.tableId == null) {
      sb.append("null");
    } else {
      sb.append(this.tableId);
    }
    sb.append(", ");
    sb.append("age:");
    sb.append(this.age);
    sb.append(", ");
    sb.append("idleTime:");
    sb.append(this.idleTime);
    sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    sb.append(", ");
    sb.append("state:");
    if (this.state == null) {
      sb.append("null");
    } else {
      sb.append(this.state);
    }
    sb.append(", ");
    sb.append("extent:");
    if (this.extent == null) {
      sb.append("null");
    } else {
      sb.append(this.extent);
    }
    sb.append(", ");
    sb.append("columns:");
    if (this.columns == null) {
      sb.append("null");
    } else {
      sb.append(this.columns);
    }
    sb.append(", ");
    sb.append("ssiList:");
    if (this.ssiList == null) {
      sb.append("null");
    } else {
      sb.append(this.ssiList);
    }
    sb.append(", ");
    sb.append("ssio:");
    if (this.ssio == null) {
      sb.append("null");
    } else {
      sb.append(this.ssio);
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}

