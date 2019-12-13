/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author ftripjr
 */
public class PokerController {
    
    // Labels for text
    @FXML    private Label rankLabel;
    @FXML    private Label suitLabel;
    @FXML    private Label rankDescripLabel;
    @FXML    private Label suitDescripLabel;
    @FXML    private Label card1Label;
    @FXML    private Label card2Label;
    @FXML    private Label card3Label;
    @FXML    private Label card4Label;
    @FXML    private Label card5Label;
    
    // Input fields
    @FXML    private TextField card1Rank;
    @FXML    private TextField card2Rank;
    @FXML    private TextField card3Rank;
    @FXML    private TextField card4Rank;
    @FXML    private TextField card5Rank;
    @FXML    private TextField card1Suit;
    @FXML    private TextField card2Suit;
    @FXML    private TextField card3Suit;
    @FXML    private TextField card4Suit;
    @FXML    private TextField card5Suit;
    @FXML    private Label pokerHandTypeLabel;
    @FXML    private Button btnHand;
    
    // 2-D Array for Cards - Rank and Suit
    private int pokerHand[][] = new int [2][5];
    
    public void makeHand(int card[], String suit[])
    {        
        for(int i=0; i < 5; i++)
        {
            // Rank in First row
            pokerHand[0][i] = card[i];
            
            // Convert Suit to int and make that second row
            switch(suit[i])
            {
                case "S":
                    pokerHand[1][i] = 0;
                    break;
                    
                case "H":
                    pokerHand[1][i] = 1;
                    break;

                case "C":
                    pokerHand[1][i] = 2;
                    break;
                    
                case "D":
                    pokerHand[1][i] = 3;
                    break;
            }
        }
    }
    
    public void cardSort(int hand[][])
    {
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < 5; i++)  
        { 
            swapped = false; 
            for (j = 0; j < 5 - i - 1; j++)  
            { 
                if (hand[0][j] > hand[0][j+1])  
                { 
                    // swap arr[j] and arr[j+1] 
                    temp = hand[0][j]; 
                    hand[0][j] = hand[0][j + 1]; 
                    hand[0][j + 1] = temp; 
                    
                    temp = hand[1][j]; 
                    hand[1][j] = hand[1][j + 1]; 
                    hand[1][j + 1] = temp; 
                    swapped = true; 
                } 
            } 

            // IF no two elements were  
            // swapped by inner loop, then break 
            if (swapped == false) 
                break; 
        } 
    }
    
    public boolean isRoyalFlush(int hand[][])
    {
        // Are they the same suit?
        boolean royalFlush = true;
        for (int i = 1; i < 5; i++)  
        {
            if(hand[1][0] != hand[1][i])
            {
                royalFlush = false;
                return royalFlush;
            }
        }

        // Are the cards the right cards?
        if(hand[0][0] != 1 || hand[0][1] != 10 || 
           hand[0][2] != 11 || hand[0][3] != 12 || 
           hand[0][4] != 13)
            royalFlush = false;

        return royalFlush;
    }
            
    public boolean isStraightFlush(int hand[][])
    {
        // Are they the same suit?
        boolean str8F = true;
        for (int i = 1; i < 5; i++)  
        {
            if(hand[1][0] != hand[1][i])
            {
                str8F = false;
                return str8F;
            }
        }

        // Are the cards in sequential order?
        for(int i = 0; i < 4; i++)
        {
            if((hand[0][i] + 1) != hand[0][i+1])
            {
                str8F = false;
                return str8F;
            }
        }
        
        return str8F;
    }
    
    // Flush
    public boolean isFlush(int hand[][])
    {
        // Are they the same suit?
        boolean str8 = true;
        
        // If the hand has an ace, check for the highest flush
        if(hand[0][0] == 1)
        {
            if(hand[0][0] == 1 && hand[0][1] == 10 && 
                hand[0][2] == 11 && hand[0][3] == 12 && 
                hand[0][4] == 13)
                str8 = true;
                return str8;
        }
        
        for (int i = 1; i < 5; i++)  
        {
            if(hand[1][0] != hand[1][i])
            {
                str8 = false;
                return str8;
            }
        }
        str8 = true;

        return str8;
    }        
    
    // Straight 
    public boolean isStraight(int hand[][])
    {
        boolean inOrder;
        
        // Are the cards in sequential order?
        for(int i = 0; i < 4; i++)
        {
            if((hand[0][i] + 1) != hand[0][i+1])
            {
                inOrder = false;
                return inOrder;
            }
        }
        inOrder = true;

        return inOrder;
    }
    
    // See if there are multiple cards of same type in hand
    public String[] ofAKind(int numCards, String kind) 
    {
        String result[] = {"false",""};
        int count = 0;
        int val;
        int thisKind = Integer.parseInt(kind);

        for(int i = 0; i < 5; i++) 
        {
            val = pokerHand[0][i];
            count = 0;

            for(int j = i + 1; j < 5; j++) 
            {
                if(pokerHand[0][j] == val) 
                {
                    count++;
                
                    if(count == (numCards - 1) && val != thisKind) 
                    {
                        result[0] = "true";
                        result[1] = Integer.toString(val);
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public boolean pair(int numPairs) {
        boolean isPair = false;

        if(numPairs == 1) 
        {
          if("true".equals(this.ofAKind(2,"-1")[0]))
            isPair = true;
        }
        else 
        {
            if("true".equals(this.ofAKind(2,"-1")[0])) {
                String val = this.ofAKind(2,"-1")[1];

            if("true".equals(this.ofAKind(2,val)[0]))
              isPair = true;
          }
        }
        return isPair;
    }

  public boolean fullHouse() {
    if("true".equals(this.ofAKind(3,"-1")[0])) {
      String val = this.ofAKind(3,"-1")[1];
      if("true".equals(this.ofAKind(2,val)[0]))
        return true;
    }
    return false;
  }
    
    public void reset() 
    {
        for(int i=0; i<2; i++)
        {
            for(int j=0; j<5; j++)
                pokerHand[i][j] = 0;
        }      
    }
    
    public void findHandType()
    {
        // Order if else by ranking of hands - Descending
        if(this.isRoyalFlush(pokerHand))
            pokerHandTypeLabel.setText("Your hand is a Royal Flush.");
        
        else if(this.isStraightFlush(pokerHand))
            pokerHandTypeLabel.setText("Your hand is a Straight Flush.");
        
        // Four of a Kind
        else if("true".equals(this.ofAKind(4,"-1")[0]))
            pokerHandTypeLabel.setText("Your hand is a Four of a Kind.");

        else if(this.fullHouse())
            pokerHandTypeLabel.setText("Your hand is a Full House.");
        
        else if(this.isFlush(pokerHand))
            pokerHandTypeLabel.setText("Your hand is a Flush.");
        
        else if(this.isStraight(pokerHand))
            pokerHandTypeLabel.setText("Your hand is a Straight.");
        
        // Three of a Kind
        else if("true".equals(this.ofAKind(3,"-1")[0]))
            pokerHandTypeLabel.setText("Your hand is a Three of a Kind.");

        // Two Pair
        else if(this.pair(2))
            pokerHandTypeLabel.setText("Your hand is a Two Pair.");

        // one pair
        else if(this.pair(1))
            pokerHandTypeLabel.setText("Your hand is a One Pair.");
        
        else
            pokerHandTypeLabel.setText("Your hand is a High Card.");
    }

    public void showHandType(ActionEvent e) {
        int cards[] = new int[5];
        String suits[] = new String[5];
        
        cards[0] = Integer.parseInt(card1Rank.getText());
        cards[1] = Integer.parseInt(card2Rank.getText());
        cards[2] = Integer.parseInt(card3Rank.getText());
        cards[3] = Integer.parseInt(card4Rank.getText());
        cards[4] = Integer.parseInt(card5Rank.getText());
        
        suits[0] = card1Suit.getText().toUpperCase();
        suits[1] = card2Suit.getText().toUpperCase();
        suits[2] = card3Suit.getText().toUpperCase();
        suits[3] = card4Suit.getText().toUpperCase();
        suits[4] = card5Suit.getText().toUpperCase();
        
        this.reset();
        this.makeHand(cards, suits);
        this.cardSort(pokerHand);
        this.findHandType();
    }    
    
}
