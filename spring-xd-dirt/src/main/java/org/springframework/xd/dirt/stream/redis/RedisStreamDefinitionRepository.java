/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.xd.dirt.stream.redis;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.xd.dirt.store.AbstractRedisRepository;
import org.springframework.xd.dirt.stream.StreamDefinition;
import org.springframework.xd.dirt.stream.StreamDefinitionRepository;

/**
 * An implementation of {@link StreamDefinitionRepository} that persists StreamDefinition in Redis.
 * 
 * @author Eric Bottard
 */
public class RedisStreamDefinitionRepository extends AbstractRedisRepository<StreamDefinition, String> implements
		StreamDefinitionRepository {

	public RedisStreamDefinitionRepository(RedisOperations<String, String> redisOperations) {
		super("stream.definitions.", redisOperations);
	}

	@Override
	protected StreamDefinition deserialize(String v) {
		String[] parts = v.split("\n");
		return new StreamDefinition(parts[0], parts[1]);
	}

	@Override
	protected String serialize(StreamDefinition entity) {
		return entity.getName() + "\n" + entity.getDefinition();
	}

	@Override
	protected String keyFor(StreamDefinition entity) {
		return entity.getName();
	}

	@Override
	protected String serializeId(String id) {
		return id;
	}

}
