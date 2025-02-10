public class GameDemo {
    public static void main(String[] args) {
        GameEntity monster = new GameEntity("Goblin", 50);
        Warrior hero = new Warrior("Hero", 100, 15);

        hero.attack(monster);
        hero.attack(monster);
        hero.attack(monster);
        hero.attack(monster);
    }
}