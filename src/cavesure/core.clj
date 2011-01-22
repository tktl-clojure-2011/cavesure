(ns cavesure.core)

(def output println)

(def starting-room nil)
(def windy-room nil)

(def *game* (atom {:inventory #{}}))

(defn load-resources [file]
  (swap! *game* #(merge % (load-file file))))

(defn start-game []
  (load-resources "resources/rooms.clj")
  (swap! *game* #(conj % [:inventory #{}]))
  (output "Welcome to Cavesure"))

(defn move [direction]
  (if-let [next-room (get-in @*game* [:exits (current-room) direction])]
    (do (swap! *game* (fn [game] (conj game [:current-room next-room] )))
        (output "You move" direction))
    (output "You can't move that way!")))

(defn current-room []
  (:current-room @*game*))

(defn items-in-current-room []
  (get-in @*game* [:items (current-room)]))

(defn inventory []
  (:inventory @*game*))

(defn player-can-see []
  (or (not= (current-room) :dark-room)
      ((:inventory @*game*) :candle)))

(defn pick-up [item]
  (if-not (player-can-see)
    "It's too dark to see anything"
    (if ((items-in-current-room) item)
      (do (swap! *game* (fn [game] (update-in
                                   (update-in game [:items (current-room)] #(disj % item))
                                   [:inventory]
                                   #(conj % item)))))
      (output "I don't see" item))))

(defn look []
  (if (and (= (current-room) :dark-room)
           (not ((:inventory @*game*) :candle)))
    "It's too dark to see."
    (get-in @*game* [:rooms (current-room)])))