package io.github.id;

import io.github.util.random.UUIDGeneratorUtils;
import org.springframework.stereotype.Component;

@Component("assistant-id-uuid")
public class UUIDGenerator implements IdGenerator<String>{

    @Override
    public String generate() {
        return UUIDGeneratorUtils.uuid();
    }
}
