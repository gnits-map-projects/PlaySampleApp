package controllers.query;

import org.junit.Test;
import play.Logger;

import static org.junit.Assert.*;


public class QueryFilterTest {

    @Test
    public void simple() throws Exception {

        QueryFilter filter = new QueryFilter("key", "value")
                .and(new QueryFilter("key2", "value2"))
                .and(new QueryFilter("key3", "value3"))
                .and(QueryFilter.or(
                        new QueryFilter("key3", "value3"),
                        new QueryFilter("key4", "value4")

                ).or(new QueryFilter("key5", "value5")));

        String queryString = filter.toString();
        // key = value AND key2 = value2 AND key3 = value3 AND (key3 = value3 OR key4 = value4) OR key5 = value5

        Logger.info(queryString);


    }

}