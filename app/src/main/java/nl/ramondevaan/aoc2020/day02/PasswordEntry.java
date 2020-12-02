package nl.ramondevaan.aoc2020.day02;

import lombok.Value;

@Value
public class PasswordEntry {
    Policy policy;
    String password;
}
