public class GameEntity {
    protected String name;
    protected int health;

    public GameEntity(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.println(name + " takes " + damage + " damage. Remaining health: " + health);
    }

    public boolean isDefeated() {
        return health <= 0;
    }

    public String getName() {
        return name;
    }
}