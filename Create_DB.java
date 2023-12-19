package FinalProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Create_DB {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/DBMSProjectfinal",
                "postgres",
                "Tiger")) {

                   

            try (PreparedStatement StockStatement = connection.prepareStatement("SELECT * FROM stock");
                 ResultSet resultSet = StockStatement.executeQuery()) {
                System.out.println("Stock");
                System.out.println("\n");
                System.out.println("prod_id" +" "+ "dept_id" + " "+  "quantity");
                System.out.println("\n");
                while (resultSet.next()) {
                    System.out.print(resultSet.getString("prod_id") + " " + resultSet.getString("dept_id") + " " + resultSet.getString("quantity"));
                    System.out.println("\n");
                }
                System.out.println("\n");
            }

           

            try (PreparedStatement DepotStatement = connection.prepareStatement("SELECT * FROM depot");
                 ResultSet resultSet = DepotStatement.executeQuery()) {
                     System.out.println("Depot");
                     System.out.println("\n");
                     System.out.print("dept_id" + " "+ "addr" + " "+  "volume");
                     System.out.println("\n");
                while (resultSet.next()) {

                    System.out.print(resultSet.getString("dept_id") + " " + resultSet.getString("addr") + " " + resultSet.getString("volume"));
                    System.out.println("\n");
                }
                System.out.println("\n");
            }

           
            try (PreparedStatement ProductStatement = connection.prepareStatement("SELECT * FROM product");
                 ResultSet resultSet = ProductStatement.executeQuery()) {
                     System.out.println("Product");
                     System.out.println("\n");
                     System.out.print("prod_id"+ " "+ "pname" + " "+ "price");
                     System.out.println("\n");
                while (resultSet.next()) {

                    System.out.print(resultSet.getString("prod_id") + " " + resultSet.getString("pname") + " " + resultSet.getString("price"));
                    System.out.println("\n");
                }
            }

            
            String alterStatementString1= "ALTER TABLE stock DROP CONSTRAINT fk_stock_product; ALTER TABLE stock ADD CONSTRAINT fk_stock_product FOREIGN KEY (prod_id) REFERENCES product(prod_id) ON DELETE CASCADE;";

            try(PreparedStatement alterStatement = connection.prepareStatement(alterStatementString1);
            ){
                int resultSet =alterStatement.executeUpdate();
                System.out.println("Table Altering has been done: " + Integer.toString(resultSet));
            }

            

            String deleteProduct = "DELETE FROM product WHERE prod_id = ?" ;
            

           
            try(
            PreparedStatement deleteProductStatement = connection.prepareStatement(deleteProduct);
            ){
                            deleteProductStatement.clearParameters();
                            deleteProductStatement.setObject(1, "P1");

                            int ProductRowsDeleted = deleteProductStatement.executeUpdate();
                            System.out.println("ProductRowsDeleted" + Integer.toString(ProductRowsDeleted));
            }

           

String alterStatementString2= "ALTER TABLE stock DROP CONSTRAINT fk_stock_depot; ALTER TABLE stock ADD CONSTRAINT fk_stock_depot FOREIGN KEY (dept_id) REFERENCES depot(dept_id) ON DELETE CASCADE;";

            try(PreparedStatement alterStatement = connection.prepareStatement(alterStatementString2);
            ){
                int resultSet =alterStatement.executeUpdate();
                System.out.println("Table Altering is done. Rows affected: " + Integer.toString(resultSet));
            }

           
            String deleteDepot = "DELETE FROM depot WHERE dept_id = ?" ;
           
            try(
            PreparedStatement deleteProductStatement = connection.prepareStatement(deleteDepot);
            ){
                deleteProductStatement.clearParameters();
                deleteProductStatement.setObject(1, "D1");

                int resultSet = deleteProductStatement.executeUpdate();
                System.out.println("Table Altering is done. Rows affected: " + Integer.toString(resultSet));
        }

       

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
