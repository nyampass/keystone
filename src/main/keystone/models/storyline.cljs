(ns keystone.models.storyline)

(defn load-storylines [repos]
  (prn :load :repos repos))

(defn gen-usecase [{:keys [repos]}]
  {:repos repos})

