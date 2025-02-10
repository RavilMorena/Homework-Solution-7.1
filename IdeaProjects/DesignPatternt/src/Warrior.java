public class Warrior extends GameEntity {
    private final int attackPower;

    public Warrior(String name, int health, int attackPower) {
        super(name, health);
        this.attackPower = attackPower;
    }

    public void attack(GameEntity target) {
        target.takeDamage(attackPower);
        System.out.println(name + " attacks " + target.getName() + " with power: " + attackPower);
        if (target.isDefeated()) {
            System.out.println(target.getName() + " has been defeated!");
        }
    }
}