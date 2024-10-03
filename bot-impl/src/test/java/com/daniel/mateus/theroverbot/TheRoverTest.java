package com.daniel.mateus.theroverbot;

import com.bueno.spi.model.CardRank;
import com.bueno.spi.model.CardSuit;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class TheRoverTest {
    private TheRover theRover;
    private GameIntel.StepBuilder stepBuilder;

    @BeforeEach
    public void setup() {
        theRover = new TheRover();
    }

    @Nested
    @DisplayName("isPlayingFirst Tests")
    class isPlayingFirstTest {

        @Test
        @DisplayName("Should return false if player is playing second")
        void ShouldReturnFalseIfPlayerIsPlayingSecond() {
            TrucoCard vira = TrucoCard.of(CardRank.ACE, CardSuit.CLUBS);
            TrucoCard opponentCard = TrucoCard.of(CardRank.FIVE, CardSuit.DIAMONDS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(List.of(), 0)
                    .opponentScore(0)
                    .opponentCard(opponentCard);

            assertFalse(theRover.isPlayingFirst(stepBuilder.build()));
        }

        @Test
        @DisplayName("Should return true if player is playing first")
        void ShouldReturnTrueIfPlayerIsPlayingFirst() {
            TrucoCard vira = TrucoCard.of(CardRank.ACE, CardSuit.CLUBS);

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(List.of(), 0)
                    .opponentScore(0);

            assertTrue(theRover.isPlayingFirst(stepBuilder.build()));
        }
    }

    @Nested
    @DisplayName("getCurrentRound tests")
    class getCurrentRoundTest {

        @Test
        @DisplayName("Should return 1 if in the first round")
        void ShouldReturnOneIfInFirstRound() {
            TrucoCard vira = TrucoCard.of(CardRank.ACE, CardSuit.CLUBS);
            List<TrucoCard> cards = List.of(
                    TrucoCard.of(CardRank.JACK, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS),
                    TrucoCard.of(CardRank.KING, CardSuit.HEARTS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(cards, 0)
                    .opponentScore(0);

            assertEquals(1, theRover.getCurrentRound(stepBuilder.build()));
        }

        @Test
        @DisplayName("Should return 2 if in the second round")
        void ShouldReturnTwoIfInSecondRound() {
            TrucoCard vira = TrucoCard.of(CardRank.ACE, CardSuit.CLUBS);
            List<TrucoCard> cards = List.of(
                    TrucoCard.of(CardRank.JACK, CardSuit.HEARTS),
                    TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(cards, 0)
                    .opponentScore(0);

            assertEquals(2, theRover.getCurrentRound(stepBuilder.build()));
        }

        @Test
        @DisplayName("Should return 3 if in the third round")
        void ShouldReturnThreeIfInThirdRound() {
            TrucoCard vira = TrucoCard.of(CardRank.ACE, CardSuit.CLUBS);
            List<TrucoCard> cards = List.of(
                    TrucoCard.of(CardRank.JACK, CardSuit.HEARTS)
            );

            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(), List.of(), vira, 1)
                    .botInfo(cards, 0)
                    .opponentScore(0);

            assertEquals(3, theRover.getCurrentRound(stepBuilder.build()));
        }
    }

    @Nested
    @DisplayName("Choose card first hand tests")
    class chooseCardFirstHandTest {

        @Nested
        @DisplayName("Playing first tests")
        class playingFirst {
            @Test
            @DisplayName("When player has one manilha one high card and one low card should return high card")
            void WhenPlayerHasOneManilhaOneHighCardAndOneLowCardShouldReturnHighCard() {
                TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS);
                List<TrucoCard> cards = List.of(
                        TrucoCard.of(CardRank.FIVE,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.THREE,CardSuit.SPADES),
                        TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS)
                );
                 stepBuilder = GameIntel.StepBuilder.with()
                         .gameInfo(List.of(), List.of(),vira, 1)
                         .botInfo(cards, 0)
                         .opponentScore(0);
                 assertEquals(TrucoCard.of(CardRank.THREE, CardSuit.SPADES), theRover.chooseCardFirstHand(stepBuilder.build()));
            }

            @Test
            @DisplayName("When player has two manilhas and one low card should return lower manilha")
            void WhenPlayerHasTwoManilhasAndOneLowCardShouldReturnLowerManilha() {
                TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS);
                List<TrucoCard> cards = List.of(
                        TrucoCard.of(CardRank.FIVE,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.FIVE,CardSuit.CLUBS),
                        TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS)
                );
                stepBuilder = GameIntel.StepBuilder.with()
                        .gameInfo(List.of(), List.of(),vira, 1)
                        .botInfo(cards, 0)
                        .opponentScore(0);
                assertEquals(TrucoCard.of(CardRank.FIVE, CardSuit.HEARTS), theRover.chooseCardFirstHand(stepBuilder.build()));
            }

            @Test
            @DisplayName("When player has three manilhas should return lower manilha")
            void WhenPlayerHasThreeManilhasShouldReturnLowerManilha() {
                TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS);
                List<TrucoCard> cards = List.of(
                        TrucoCard.of(CardRank.FIVE,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.FIVE,CardSuit.CLUBS),
                        TrucoCard.of(CardRank.FIVE, CardSuit.DIAMONDS)
                );
                stepBuilder = GameIntel.StepBuilder.with()
                        .gameInfo(List.of(), List.of(),vira, 1)
                        .botInfo(cards, 0)
                        .opponentScore(0);
                assertEquals(TrucoCard.of(CardRank.FIVE, CardSuit.DIAMONDS), theRover.chooseCardFirstHand(stepBuilder.build()));
            }

            @Test
            @DisplayName("When player has three regular card should return higher card")
            void WhenPlayerHasThreeRegularCardsShouldReturnHigherCard() {
                TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS);
                List<TrucoCard> cards = List.of(
                        TrucoCard.of(CardRank.ACE,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.THREE,CardSuit.SPADES),
                        TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS)
                );
                stepBuilder = GameIntel.StepBuilder.with()
                        .gameInfo(List.of(), List.of(),vira, 1)
                        .botInfo(cards, 0)
                        .opponentScore(0);
                assertEquals(TrucoCard.of(CardRank.THREE, CardSuit.SPADES), theRover.chooseCardFirstHand(stepBuilder.build()));
            }

            @Test
            @DisplayName("When player has three cards of the same value return any of then")
            void WhenPlayerHasThreeCardsOfSameValue() {
                TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS);
                List<TrucoCard> cards = List.of(
                        TrucoCard.of(CardRank.ACE,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.ACE,CardSuit.SPADES),
                        TrucoCard.of(CardRank.ACE, CardSuit.DIAMONDS)
                );
                stepBuilder = GameIntel.StepBuilder.with()
                        .gameInfo(List.of(), List.of(),vira, 1)
                        .botInfo(cards, 0)
                        .opponentScore(0);
                assertThat(theRover.chooseCardFirstHand(stepBuilder.build())).isIn(
                        TrucoCard.of(CardRank.ACE,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.ACE,CardSuit.SPADES),
                        TrucoCard.of(CardRank.ACE, CardSuit.DIAMONDS)
                );
            }
        }

        @Nested
        @DisplayName("Playing second tests")
        class playingSecond {

            @Test
            @DisplayName("Should play only card that beat opponent card")
            void ShouldPlayOnlyCardThatBeatOpponentCard() {
                TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS);
                TrucoCard opponentCard = TrucoCard.of(CardRank.TWO, CardSuit.DIAMONDS);
                List<TrucoCard> cards = List.of(
                        TrucoCard.of(CardRank.ACE,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.THREE,CardSuit.SPADES),
                        TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS)
                );

                stepBuilder = GameIntel.StepBuilder.with()
                        .gameInfo(List.of(), List.of(), vira, 1)
                        .botInfo(cards, 0)
                        .opponentScore(0)
                        .opponentCard(opponentCard);

                assertEquals(TrucoCard.of(CardRank.THREE, CardSuit.SPADES), theRover.chooseCardFirstHand(stepBuilder.build()));
            }

            @Test
            @DisplayName("When two cards beat opponent card should use lowest card")
            void WhenTwoCardsBeatOpponentCardShouldUseLowestCard() {
                TrucoCard vira = TrucoCard.of(CardRank.FOUR, CardSuit.CLUBS);
                TrucoCard opponentCard = TrucoCard.of(CardRank.ACE, CardSuit.DIAMONDS);
                List<TrucoCard> cards = List.of(
                        TrucoCard.of(CardRank.TWO,CardSuit.HEARTS),
                        TrucoCard.of(CardRank.THREE,CardSuit.SPADES),
                        TrucoCard.of(CardRank.SEVEN, CardSuit.DIAMONDS)
                );

                stepBuilder = GameIntel.StepBuilder.with()
                        .gameInfo(List.of(), List.of(), vira, 1)
                        .botInfo(cards, 0)
                        .opponentScore(0)
                        .opponentCard(opponentCard);

                assertEquals(TrucoCard.of(CardRank.TWO, CardSuit.HEARTS), theRover.chooseCardFirstHand(stepBuilder.build()));
            }



        }

    }
}