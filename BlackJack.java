//Brian Carreno
//
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BlackJack {

  private static ArrayList<Card> cards = new ArrayList<>(); // added an arraylist for cards
  private static int currentCardIndex = 0;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean turn = true;
    String playerDecision = "";
    //added a for loop so the game can be played multiple times
    //for (int i = 0; i < turn; i++) {
    while(turn) {
      initializeDeck();
      //shuffleDeck();
      shuffleDeck();  // Shuffle deck with the new array list
      int playerTotal = 0;
      int dealerTotal = 0;
      playerTotal = dealInitialPlayerCards();
      //fix dealInitialDealerCards
      dealerTotal = dealInitialDealerCards();

      //fix playerTurn
      playerTotal = playerTurn(scanner, playerTotal);
      if (playerTotal > 21) {
        System.out.println("You busted! Dealer wins.");
        return;
      }

      //fix dealerTurn
      dealerTotal = dealerTurn(dealerTotal);
      determineWinner(playerTotal, dealerTotal);

      System.out.println("Would you like to play another hand?");
      playerDecision = scanner.nextLine().toLowerCase();
      //added
      //asks player if they want to play again
      while(!(playerDecision.equals("no") || (playerDecision.equals("yes")))) {
        System.out.println("Invalid action. Please type 'yes' or 'no'.");
        playerDecision = scanner.nextLine().toLowerCase();
      }
      
      if (playerDecision.equals("no")) {
        turn = false;
      }
    }
    System.out.println("Thanks for playing!");
  }
  // algorithm to create deck
  // Initialize deck with ArrayList
  private static void initializeDeck() {
    //for (int i = 0; i < DECK.length; i++) {
    String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
    
    cards.clear();  // Clear the ArrayList to reset the deck

    for (String suit : SUITS) {
      for (String rank : RANKS) {
        int value = 10;
        if (!rank.equals("Jack") && !rank.equals("Queen") && !rank.equals("King") && !rank.equals("Ace")) {
          value = Integer.parseInt(rank);
        } else if (rank.equals("Ace")) {
          value = 11;
        }
        cards.add(new Card(value, suit, rank));
      }
    }
  }
  // algorithm to shuffle deck
  // Shuffle the deck using ArrayList
  private static void shuffleDeck() {
    Random random = new Random();
    for (int i = 0; i < cards.size(); i++) {
      int index = random.nextInt(cards.size());
      Card temp = cards.get(i);
      cards.set(i, cards.get(index));
      cards.set(index, temp);
    }
  }
  // algorithm to deal initial player cards
  // Deal initial player cards
  private static int dealInitialPlayerCards() {
    /*int card1 = dealCard();
    int card2 = dealCard();*/
    Card card1 = dealCard();
    Card card2 = dealCard();
    //System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[card1 / 13] + " and " + RANKS[card2] + " of " + SUITS[card2 / 13]);
    System.out.println("Your cards: " + card1 + " and " + card2);
    
    //return cardValue(card1) + cardValue(card2);
    return card1.getValue() + card2.getValue();
  }
  // alogrithm to deal initial dealer cards
  // Deal initial dealer cards
  private static int dealInitialDealerCards() {
    Card card1 = dealCard();
    System.out.println("Dealer's card: " + card1);
    return card1.getValue();
  }

  private static int playerTurn(Scanner scanner, int playerTotal) {
    while (true) {
      System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
      String action = scanner.nextLine().toLowerCase();
      if (action.equals("hit")) {
        Card newCard = dealCard();
        playerTotal += newCard.getValue();
        System.out.println("You drew a " + newCard);
        if (playerTotal > 21) {
          //added
          //resets playerTotal so the game can be played multiple times
          System.out.println("You busted! Dealer wins.");
          return playerTotal;
        }
      } else if (action.equals("stand")) {
        break;
      } else {
        System.out.println("Invalid action. Please type 'hit' or 'stand'.");
      }
    }
    return playerTotal;
  }

  // algorithm for dealer's turn
  private static int dealerTurn(int dealerTotal) {
    while (dealerTotal < 17) {
      Card newCard = dealCard();
      dealerTotal += newCard.getValue();
    }
    System.out.println("Dealer's total is " + dealerTotal);
    return dealerTotal;
  }

  // algorithm to determine the winner
  private static void determineWinner(int playerTotal, int dealerTotal) {
    if (dealerTotal > 21 || playerTotal > dealerTotal) {
      System.out.println("You win!");
    } else if (dealerTotal == playerTotal) {
      System.out.println("It's a tie!");
    } else {
      System.out.println("Dealer wins!");
    }
  }

  // algroithm to deal a card
  //private static int dealCard() {
  private static Card dealCard() {
    //return DECK[currentCardIndex++] % 13;
    return cards.remove(0);  // Removing the top card from the deck after it has been dealt
  }

  // algorithm to determine card value
  private static int cardValue(int card) {
    return card < 9 ? card + 2 : 10;
  }

  // Card class
  public static class Card {
    private int value;
    private String suit;
    private String rank;

    public Card(int value, String suit, String rank) {
      this.value = value;
      this.suit = suit;
      this.rank = rank;
    }

    public int getValue() {
      return value;
    }

    public String getSuit() {
      return suit;
    }

    public String getRank() {
      return rank;
    }

    public void setValue(int value) {
      this.value = value;
    }

    public void setSuit(String suit) {
      this.suit = suit;
    }

    public void setRank(String rank) {
      this.rank = rank;
    }

    public String toString() {
      return rank + " of " + suit;
    }
  }
}
