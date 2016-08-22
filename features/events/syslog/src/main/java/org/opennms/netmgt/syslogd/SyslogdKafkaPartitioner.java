/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2016-2016 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2016 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.syslogd;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.opennms.core.utils.InetAddressUtils;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

public class SyslogdKafkaPartitioner implements Partitioner {

    @Override
    public void configure(Map<String, ?> configs) { }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();

        SyslogConnection syslogConn = (SyslogConnection)key;
        InetAddress sourceAddress = syslogConn.getSourceAddress();
        int partition = 0;
        Random rnd = new Random();
        if(sourceAddress == null) {
            partition =  rnd.nextInt(255);
        } else {
            String stringKey = InetAddressUtils.toIpAddrString(sourceAddress);
            int offset = stringKey.lastIndexOf('.');
            if (offset > 0) {
                partition = Integer.parseInt( stringKey.substring(offset+1)) % numPartitions;
            }
        }
        return partition;
    }

    @Override
    public void close() { }

}
