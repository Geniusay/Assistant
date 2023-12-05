package io.github.id;

import org.springframework.data.annotation.Id;

public interface IdGenerator<T> {

    T generate() throws IdGeneratorException;

    default T safeGenerate(){
        try {
            return generate();
        }catch (IdGeneratorException e){
            return null;
        }
    }
}
