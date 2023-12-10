package org.brunovolpato.calcite.learn;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;

public class L02_QueryVisitor {

  public static void main(String[] args) throws SqlParseException {

    SqlParser parser =
        SqlParser.create(
            "SELECT \"name\", SUM(\"salary\" * 1.1) FROM \"foo\" WHERE \"bar\"=3 GROUP BY 1");
    SqlNode query = parser.parseQuery();

    System.out.println(query);

    System.out.println("===================");
    System.out.println("Visitor:");
    System.out.println("");
    query.accept(new PrintingVisitor());
  }

  static class PrintingVisitor extends SqlBasicVisitor<Void> {
    @Override
    public Void visit(SqlLiteral literal) {
      System.out.println("SqlLiteral: " + literal);
      return super.visit(literal);
    }

    @Override
    public Void visit(SqlIdentifier id) {
      System.out.println("SqlIdentifier: " + id);
      return super.visit(id);
    }

    @Override
    public Void visit(SqlCall call) {
      // System.out.println("SqlCall: " + call);
      return super.visit(call);
    }

    @Override
    public Void visit(SqlNodeList nodeList) {
      System.out.println("SqlNodeList: " + nodeList);
      return super.visit(nodeList);
    }

    @Override
    public Void visit(SqlDataTypeSpec type) {
      System.out.println("SqlDataTypeSpec: " + type);
      return super.visit(type);
    }

    @Override
    public Void visit(SqlDynamicParam param) {
      System.out.println("SqlDynamicParam: " + param);
      return super.visit(param);
    }

    @Override
    public Void visit(SqlIntervalQualifier intervalQualifier) {
      System.out.println("SqlIntervalQualifier: " + intervalQualifier);
      return super.visit(intervalQualifier);
    }
  }
}
