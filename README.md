## Task 2
Create the following interfaces and classes:
* **interface DrinkRecipe** with methods: String getName() and DrinkRecipe addComponent(String componentName, int componentCount).

* **interface DrinkPreparation** with method Map<String, Integer> makeDrink() - returns the drink components.

* **interface Rating** with method int getRating().

---
* Class Coffee contains fields String name, int rating, Map<String,Integer> ingredients and implements interfaces DrinkRecipe, DrinkPreparation and Rating.
  Method makeDrink() prepares coffee with typically components: {Water=100, Arabica=20}.
* **Espresso** and **Cappuccino** classes extends the Coffee class and override method makeDrink(). Espresso coffee has 50 g. of Water. Cappuccino coffee has an additional 50 g. of Milk.

---
* Method **averageRating(List<Coffee> coffeeList)** of the MyUtils class returns a Map with coffee name as key and average coffee rating as value.

---
For example, for a given list:

---
* [Espresso [name=Espresso, rating=8], 
* Cappuccino [name=Cappuccino, rating=10], 
* Espresso [name=Espresso, rating=10], 
* Cappuccino [name=Cappuccino, rating=6], 
* Coffee [name=Coffee, rating=6]]
---
you should get
{Espresso=9.00, Cappuccino=8.00, Coffee=6.00}

---
### When you will paste code to Moodle, leave only one public class 'MyUtils' another classes and interphase must be without modifier access

