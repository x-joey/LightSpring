package beans;

public class PropertyValue {
    private final String name;
    private final Object value;
    private Object convertedValue;
    private boolean converted = false;//当xml文件中出现ref指向另一个bean时，我们需要将将ref中的值，转为指向的哪个对象，并实例化该对象；
    // 也就是说成员变量是一个bean对象

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
    public synchronized boolean isConverted() {
        return this.converted;
    }


    public synchronized void setConvertedValue(Object value) {
        this.converted = true;
        this.convertedValue = value;
    }

    public synchronized Object getConvertedValue() {
        return this.convertedValue;
    }
}
