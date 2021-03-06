/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.server.test.randomwalk.security;

import java.util.Properties;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.security.SystemPermission;
import org.apache.accumulo.core.security.TablePermission;
import org.apache.accumulo.server.test.randomwalk.State;
import org.apache.accumulo.server.test.randomwalk.Test;

public class DropUser extends Test {
  
  @Override
  public void visit(State state, Properties props) throws Exception {
    Connector conn = SecurityHelper.getSystemConnector(state);
    
    String tableUserName = SecurityHelper.getTabUserName(state);
    
    boolean exists = SecurityHelper.getTabUserExists(state);
    boolean hasPermission = false;
    if (SecurityHelper.getSysPerm(state, SecurityHelper.getSysUserName(state), SystemPermission.DROP_USER)) hasPermission = true;
    try {
      conn.securityOperations().dropUser(tableUserName);
    } catch (AccumuloSecurityException ae) {
      switch (ae.getErrorCode()) {
        case PERMISSION_DENIED:
          if (hasPermission) throw new AccumuloException("Got a security exception when I should have had permission.", ae);
          else {
            if (exists) {
              state.getConnector().securityOperations().dropUser(tableUserName);
              SecurityHelper.setTabUserExists(state, false);
              for (TablePermission tp : TablePermission.values())
                SecurityHelper.setTabPerm(state, tableUserName, tp, false);
              for (SystemPermission sp : SystemPermission.values())
                SecurityHelper.setSysPerm(state, tableUserName, sp, false);
            }
            return;
          }
          
        case USER_DOESNT_EXIST:
          if (exists) throw new AccumuloException("Got user DNE exception when user should exists.", ae);
          else return;
        default:
          throw new AccumuloException("Got unexpected exception", ae);
      }
    }
    SecurityHelper.setTabUserExists(state, false);
    for (TablePermission tp : TablePermission.values())
      SecurityHelper.setTabPerm(state, tableUserName, tp, false);
    for (SystemPermission sp : SystemPermission.values())
      SecurityHelper.setSysPerm(state, tableUserName, sp, false);
    if (!hasPermission) throw new AccumuloException("Didn't get Security Exception when we should have");
  }
}
