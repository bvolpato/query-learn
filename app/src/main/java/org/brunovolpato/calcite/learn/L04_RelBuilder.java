package org.brunovolpato.calcite.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.RelBuilder;

public class L04_RelBuilder {

  public static void main(String[] args) throws SQLException {

    // Create a Calcite connection
    Properties info = new Properties();
    info.setProperty("lex", Lex.MYSQL.name());
    Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
    CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
    SchemaPlus rootSchema = calciteConnection.getRootSchema();
    rootSchema.add("employee", new SimpleEmployeeSchemaFactory.SimpleEmployeeTable());

    // Create a RelBuilder
    RelBuilder builder =
        RelBuilder.create(
            Frameworks.newConfigBuilder()
                .defaultSchema(rootSchema)
                .typeSystem(RelDataTypeSystem.DEFAULT)
                .build());

    // Build a relational expression
    RelNode node =
        builder
            .scan("employee")
            .project(builder.field("id"), builder.field("name"), builder.field("position"))
            .aggregate(builder.groupKey("position"))
            .filter(builder.equals(builder.field("position"), builder.literal("Software Engineer")))
            .build();

    // Print the relational expression
    System.out.println(RelOptUtil.toString(node));
  }
}

class SimpleEmployeeSchemaFactory implements SchemaFactory {
  public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
    return new SimpleEmployeeSchema();
  }

  static class SimpleEmployeeSchema extends AbstractSchema {
    @Override
    protected Map<String, Table> getTableMap() {
      Map<String, Table> tables = new HashMap<>();
      tables.put("employee", new SimpleEmployeeTable());
      return tables;
    }
  }

  static class SimpleEmployeeTable extends AbstractTable {
    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
      RelDataTypeFactory.Builder builder = new RelDataTypeFactory.Builder(typeFactory);
      builder.add("id", SqlTypeName.INTEGER);
      builder.add("name", SqlTypeName.VARCHAR, 100);
      builder.add("position", SqlTypeName.VARCHAR, 100);
      return builder.build();
    }
  }
}
