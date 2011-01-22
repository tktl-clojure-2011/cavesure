(ns cavesure.test.core
  (:use cavesure.core :reload)
  (:use midje.sweet))

(fact "game starts in a room"
      (do (start-game)
          (current-room) => truthy))

(fact "starting room has a candle"
      (do (start-game)
          (items-in-current-room) => (contains #{:candle})))

(fact "player can pick up candle"
      (do (start-game)
          (pick-up :candle)
          (items-in-current-room) => #{}))

(fact "player can move south"
      (do (start-game)
          (move :south)
          (current-room) => :dark-room))

(fact "player can't see in the dark room without a candle"
      (do (start-game)
          (move :south)
          (look) => #"too dark"))

(fact "player can't pick up the sword when he doesn't see it"
      (do (start-game)
          (move :south)
          (pick-up :sword) => #"too dark"))

(fact "player can see in the dark room with a candle"
      (do (start-game)
          (pick-up :candle)
          (move :south)
          (look) => #"big"))

(fact "player can pick up sword if he has the candle"
      (do (start-game)
          (pick-up :candle)
          (move :south)
          (pick-up :sword)
          (inventory) => (contains #{:sword :candle})))