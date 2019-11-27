package homework5;

public class Demo {
    public static void main(String[] args) throws Exception{
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product();
        product.setId(1);
        product.setName("Vitalik");



        productRepository.delete(1);
        //productRepository.update();
    }
}
