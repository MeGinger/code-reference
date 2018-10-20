package amazon.ood;

enum SQLKeyword {
	SELECT, FROM, WHERE, AND, OR
}

// http://blog.jobbole.com/55086/
abstract class SQLStatement {
}

class SelectStatement extends SQLStatement {
}

class FromStatement extends SQLStatement {
}

class WhereStatement extends SQLStatement {
}

class GroupByStatement extends SQLStatement {
}

class HavingStatement extends SQLStatement {
}

class UnionStatement extends SQLStatement {
}

class OrderByStatement extends SQLStatement {
}

// 不是compiler，这里强调如何解析SQL语句中的变量，
// 比如table name，和关键字， 比如select 语句）
// 关键在于怎么用好interface
// https://stackoverflow.com/questions/10379956/parsing-sql-like-syntax-design-pattern
public class SQLParser {
}
