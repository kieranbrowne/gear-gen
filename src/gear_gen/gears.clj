(ns gear-gen.core
  (:require [clojure.spec.alpha :as s]))


(s/dev ::gear 
       (s/keys :req  [::pitch-radius
                      ::base-radius
                      ::outside-radius
                      ::root-radius
                      ::addendum ;; radial dist between pitch and outside circles
                      ::dedendum ;; radial dist between pitch and root circles
                      ::tooth-thickness ;; thickness along pitch-circle
                      ::circlular-pitch ;; length of arc along pitch circle along corresponding points of two teeth
                      ::x
                      ::y
                      ]
               :opt  []))

(Math/pow 4 2)
(defn meshes? [gear1 gear2]
  (and
    ;; pitch circles touch
    (= (+ (:pitch-radius gear1) (:pitch-radius gear2))
      (Math/sqrt 
        (+ (Math/pow (- (:x gear1) (:x gear2)) 2)
           (Math/pow (- (:y gear1) (:y gear2)) 2)
           )))

    ;; teeth are same size
    (= (:pitch gear1) (:pitch gear1))
    (= (:tooth-thickness gear1) (:tooth-thickness gear1))


    ))
