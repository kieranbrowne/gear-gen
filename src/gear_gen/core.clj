(ns gear-gen.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def pi 3.14)
(def two-pi (* pi 2))

(defn rpos [norm rad]
  [(* (q/sin (* two-pi norm)) rad)
   (* (q/cos (* two-pi norm)) rad)])

(defn draw-tooth [gear i]
  (let [spline (* i (/ 1 (:teeth gear)))]
    ;; low
    (apply q/vertex (rpos (- spline 0.03)  (:root-radius gear)))
    ;; main pitch
    (apply q/vertex (rpos (- spline 0.02)  (:pitch-radius gear)))

    ;; peak
    (apply q/vertex (rpos (- spline 0.005)  (:outside-radius gear)))
    (apply q/vertex (rpos (+ spline 0.005)  (:outside-radius gear)))

    (apply q/vertex (rpos (+ spline 0.02)  (:pitch-radius gear)))
    ;; low
    (apply q/vertex (rpos (+ spline 0.03)  (:root-radius gear)))
  ))

(defn draw-gear [gear]
  (q/stroke 255)
  (q/no-fill)
  (q/with-translation ((juxt :x :y) gear)
    (q/with-rotation [(+ (* (q/frame-count) (:speed gear) 1/100) (:offset gear))]
      (q/rect -5 -5 10 10)
      (q/begin-shape)
      (dotimes [i (:teeth gear)]
        (draw-tooth gear i)
        )
      (q/end-shape :close))

      (q/stroke 255 150)
      (q/ellipse 0 0 (* 2 (:pitch-radius gear)) (* 2 (:pitch-radius gear)))
      (q/ellipse 0 0 (* 2 (:outside-radius gear)) (* 2 (:outside-radius gear)))
      (q/ellipse 0 0 (* 2 (:root-radius gear)) (* 2 (:root-radius gear)))

    )
  )

(defn setup []
  {})

(defn update-state [state]
  {})

(defn draw-state [state]
  (q/background 0 0 255)
  (draw-gear 
    {:pitch-radius 80
     :outside-radius 90
     :root-radius 70
     :teeth 9
     :offset 0
     :speed 1
     :pitch (/ (* 2 80 pi) 9)
     :x 200
     :y 200})
  (draw-gear 
    {:pitch-radius 80
     :outside-radius 90
     :root-radius 70
     :speed -9/8
     :offset -1.27
     :teeth 8
     :pitch (/ (* 2 80 pi) 9)
     :x 360
     :y 200})
  )

(defn -main [& args]
  (q/defsketch gear-gen
    :title "Gears"
    :size [500 500]
    ; setup function called only once, during sketch initialization.
    :renderer :java2d
    :setup setup
    ; update-state is called on each iteration before draw-state.
    :update update-state
    :draw draw-state
    :features [:keep-on-top]
    ; This sketch uses functional-mode middleware.
    ; Check quil wiki for more info about middlewares and particularly
    ; fun-mode.
    :middleware [m/fun-mode]))
