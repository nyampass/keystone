(ns frameworks.phaser.debug)

(def main-scene (atom nil))

(defn set-main-scene! [scene]
  (reset! main-scene scene))
