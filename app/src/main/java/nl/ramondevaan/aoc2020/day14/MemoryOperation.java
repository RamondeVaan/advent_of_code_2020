package nl.ramondevaan.aoc2020.day14;

import lombok.Value;

@Value
public class MemoryOperation implements Operation {

    public Long memoryAddress;
    public long value;
}
