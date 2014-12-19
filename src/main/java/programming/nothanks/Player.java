package programming.nothanks;

import java.util.List;

import com.google.common.collect.Lists;


public class Player {
	
	protected Game game;
	
	protected Integer availablesChips;
	
	protected List<List<Integer>> cardGroups;
	
	public Player() {
		cardGroups = Lists.<List<Integer>> newArrayList();
	}

	public void play() {
		
		int card = game.getTableCard();
		if (formGroup(card)) {
			
		} else {
			
		}
		
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setAvailableChips(int chips) {
		this.availablesChips = chips;
	}
	
	protected boolean formGroup(int card) {
		for (List<Integer> group : cardGroups) {
			int 
			boolean formGroup = true;
			for (Integer groupCard : group) {
				formGroup = formGroup && Math.abs(groupCard - card) == 1;
			}
			if (formGroup) {
				return formGroup;
			}
		}
		return false;
	}
	
	protected int calculateMyBenefit(int card) {
		
	}

}
