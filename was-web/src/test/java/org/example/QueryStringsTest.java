package org.example;

import org.example.http.request.query.QueryStrings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringsTest {

    @Test
    void createTest() {
        QueryStrings queryStrings = new QueryStrings("operand1=11&operator=*&operand2=55");

        assertThat(queryStrings).isNotNull();
    }
}