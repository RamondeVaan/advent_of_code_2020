package nl.ramondevaan.aoc2020.day14;

import lombok.Value;

@Value
public class MaskOperation implements Operation {

    public long maskWithZeros;
    public long maskWithOnes;
}
