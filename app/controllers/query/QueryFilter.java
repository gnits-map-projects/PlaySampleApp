package controllers.query;

public class QueryFilter {

    private String field;
    private String value;

    private QueryFilter left;
    private QueryFilter right;

    private String operator;

    private String prefix = "";
    private String postfix = "";

    public QueryFilter(String field, String operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public QueryFilter(String field, String value) {
        this(field, "=", value);
    }

    public QueryFilter(QueryFilter left, String operator, QueryFilter right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public QueryFilter and(QueryFilter other) {
        return new QueryFilter(this, "AND", other);
    }

    public QueryFilter or(QueryFilter other) {
        return new QueryFilter(this, "OR", other);
    }

    public static QueryFilter and(QueryFilter... queryFilters) {
        QueryFilter combinedFilter = null;
        for (QueryFilter filter : queryFilters) {
            combinedFilter = (combinedFilter == null) ? filter : combinedFilter.and(filter);
        }
        combinedFilter.prefix = "(";
        combinedFilter.postfix = ")";
        return combinedFilter;
    }

    public static QueryFilter or(QueryFilter... queryFilters) {
        QueryFilter combinedFilter = null;
        for (QueryFilter filter : queryFilters) {
            combinedFilter = (combinedFilter == null) ? filter : combinedFilter.or(filter);
        }
        combinedFilter.prefix = "(";
        combinedFilter.postfix = ")";
        return combinedFilter;
    }

    @Override
    public String toString() {

        if (null != left) {
            return String.format("%s%s %s %s%s", prefix, left, operator, right, postfix);
        } else if (value != null) {
            return String.format("%s %s %s", field, operator, value);
        } else {
            return "";
        }

    }
}