package local.luke.crypto.model;

import local.luke.crypto.exception.CryptoException;

public class Crypto {

    private static int lastId = 0;
    private Integer id;
    private String name;
    private String symbol;
    private Double price;
    private Double quantity;

    public Crypto(String name, String symbol, Double price, Double quantity) throws CryptoException {
        id = ++lastId;
        this.name = name;
        this.symbol = symbol;
        setPrice(price);
        setQuantity(quantity);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) throws CryptoException {
        if (price <= 0) {
            throw new CryptoException("Hodnota cena nesmí být záporná. Zadal jsi " + price + ".");
        }
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) throws CryptoException {
        if (quantity <= 0) {
            throw new CryptoException("Hodnota množství nesmí být záporná. Zadal jsi hodnotu " + quantity + ".");
        }
        this.quantity = quantity;
    }

}
