package head_first_design_pattern.Iterator;

import java.util.Hashtable;

/**
 * Created by gudi on 5/17/15.
 */
public class CafeMenu {

    Hashtable menuItems = new Hashtable();

    public CafeMenu() {
        addItem("Vegeie Burger and Air Fries",
                "Veggie burger on a whole wheat bun, lettuce, tomato, and fries",
                true, 3.99);

        addItem("Soup of the day",
                "A cup of the soup of the day, with a side salad",
                false, 3.69);

        addItem("Burrito",
                "A large burrito, with whole pinto beans, salsa, guacamole",
                true, 4.29);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.put(menuItem.getName(), menuItem);
    }

    public Hashtable getItems() {
        return menuItems;
    }
}
