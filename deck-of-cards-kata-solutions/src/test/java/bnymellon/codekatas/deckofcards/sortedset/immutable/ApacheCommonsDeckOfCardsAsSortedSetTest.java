/*
 * Copyright 2022 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.codekatas.deckofcards.sortedset.immutable;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import bnymellon.codekatas.deckofcards.Card;
import bnymellon.codekatas.deckofcards.Rank;
import bnymellon.codekatas.deckofcards.Suit;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApacheCommonsDeckOfCardsAsSortedSetTest
{
    private JDKImperativeDeckOfCardsAsSortedSet jdkDeck = new JDKImperativeDeckOfCardsAsSortedSet();
    private ApacheCommonsDeckOfCardsAsSortedSet acDeck = new ApacheCommonsDeckOfCardsAsSortedSet();

    @Test
    public void allCards()
    {
        Assertions.assertEquals(this.jdkDeck.getCards(), this.acDeck.getCards());
    }

    @Test
    public void cardsAreImmutable()
    {
        var acCards = this.acDeck.getCards();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCards::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCards.add(null));
    }

    @Test
    public void diamonds()
    {
        Assertions.assertEquals("|A♦|, |2♦|, |3♦|, |4♦|, |5♦|, |6♦|, |7♦|, |8♦|, |9♦|, |10♦|, |J♦|, |Q♦|, |K♦|",
                Iterate.makeString(this.acDeck.diamonds()));
    }

    @Test
    public void hearts()
    {
        Assertions.assertEquals("|A♥|, |2♥|, |3♥|, |4♥|, |5♥|, |6♥|, |7♥|, |8♥|, |9♥|, |10♥|, |J♥|, |Q♥|, |K♥|",
                Iterate.makeString(this.acDeck.hearts()));
    }

    @Test
    public void spades()
    {
        Assertions.assertEquals("|A♠|, |2♠|, |3♠|, |4♠|, |5♠|, |6♠|, |7♠|, |8♠|, |9♠|, |10♠|, |J♠|, |Q♠|, |K♠|",
                Iterate.makeString(this.acDeck.spades()));
    }

    @Test
    public void clubs()
    {
        Assertions.assertEquals("|A♣|, |2♣|, |3♣|, |4♣|, |5♣|, |6♣|, |7♣|, |8♣|, |9♣|, |10♣|, |J♣|, |Q♣|, |K♣|",
                Iterate.makeString(this.acDeck.clubs()));
    }

    @Test
    public void deal()
    {
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> acShuffle = this.acDeck.shuffle(new Random(1));

        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> acHand = this.acDeck.deal(acShuffle, 5);
        Assertions.assertEquals(jdkHand, acHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> acHands = this.acDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assertions.assertEquals(jdkHands, acHands);
    }

    @Test
    public void dealHands()
    {
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> acShuffled = this.acDeck.shuffle(new Random(1));
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        List<Set<Card>> acHands = this.acDeck.dealHands(acShuffled, 5, 5);
        Assertions.assertEquals(jdkHands, acHands);
    }

    @Test
    public void cardsBySuit()
    {
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        MultiValuedMap<Suit, Card> acCardsBySuit = this.acDeck.getCardsBySuit();
        Assertions.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), new TreeSet<>(acCardsBySuit.get(Suit.CLUBS)));
    }

    @Test
    public void cardsBySuitIsImmutable()
    {
        var acCardsBySuit = this.acDeck.getCardsBySuit();
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.remove(Suit.CLUBS));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit::clear);
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).remove(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> acCardsBySuit.get(Suit.CLUBS).add(null));
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                acCardsBySuit.get(Suit.CLUBS)::clear);
    }

    @Test
    public void countsBySuit()
    {
        Bag<Suit> bag = new HashBag<>();
        bag.add(Suit.CLUBS, 13);
        bag.add(Suit.DIAMONDS, 13);
        bag.add(Suit.SPADES, 13);
        bag.add(Suit.HEARTS, 13);
        Assertions.assertEquals(bag, this.acDeck.countsBySuit());
    }

    @Test
    public void countsByRank()
    {
        MultiSet<Rank> set = new HashMultiSet<>();
        set.add(Rank.ACE, 4);
        set.add(Rank.KING, 4);
        set.add(Rank.QUEEN, 4);
        set.add(Rank.JACK, 4);
        set.add(Rank.TWO, 4);
        set.add(Rank.THREE, 4);
        set.add(Rank.FOUR, 4);
        set.add(Rank.FIVE, 4);
        set.add(Rank.SIX, 4);
        set.add(Rank.SEVEN, 4);
        set.add(Rank.EIGHT, 4);
        set.add(Rank.NINE, 4);
        set.add(Rank.TEN, 4);
        Assertions.assertEquals(set, this.acDeck.countsByRank());
    }
}
