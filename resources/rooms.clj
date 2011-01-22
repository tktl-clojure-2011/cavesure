{:rooms {:windy-room "You are in a windy room."
         :dark-room "You are in a big, dark room."}
 :items {:windy-room #{:candle}
         :dark-room #{:sword}}
 :exits {:windy-room {:south :dark-room}
         :dark-room {:north :windy-room}}
 :current-room :windy-room}
