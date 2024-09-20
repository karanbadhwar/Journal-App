package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class userArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
//        return Stream.of(Arguments.of("Karan"), Arguments.of("Badhwar Karan"));
        return Stream.of(Arguments.of(User.builder().userName("Bhoooola").password("").build()));
    }
}
