package programming.nothanks;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Game {
	
	private List<Player> players;
	private Integer tableCard;
	private Stack<Integer> cardStack;
	private Integer cardChips;
	
	public Game(int cardAmount, int minCard, List<Player> players)  {
		initTable(cardAmount, minCard);
		initPlayers(players);
	}

	private void initPlayers(List<Player> players) {
		setPlayers(players);
		Collections.shuffle(players);
		for (Player player : players) {
			player.setGame(this);
			player.setAvailableChips(11);
		}
	}

	private void initTable(int cardAmount, int minCard) {
		setCardStack(new Stack<Integer>());
		for (int i = minCard; i <= cardAmount; i++) {
			getCardStack().add(Integer.valueOf(i));
		}
		Collections.shuffle(getCardStack());
		setTableCard(getCardStack().pop());
		removeNineCards();
		setCardChips(0);
	}
	
	private void removeNineCards() {
		for (int i = 0; i < 9; i++) {
			cardStack.pop();
		}
	}

	public void playRound() {
		for (Player player : players) {
			player.play();
		}
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	
	public boolean hasFinished() {
		return cardStack.isEmpty();
	}
	
	public Integer getTableCard() {
		return tableCard;
	}

	public Integer getCardChips() {
		return cardChips;
	}

	public void setCardChips(Integer cardChips) {
		this.cardChips = cardChips;
	}
	
	public Integer takeTableCard() {
		Integer chips = getCardChips();
		setCardChips(0);
		return chips;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setTableCard(Integer tableCard) {
		this.tableCard = tableCard;
	}

	public Stack<Integer> getCardStack() {
		return cardStack;
	}
	
	public void setCardStack(Stack<Integer> cardStack) {
		this.cardStack = cardStack;
	}
}

	