(ns keystone.application.repository)

(defprotocol Repository
  (save-storylines [this])
  (load-storylines [this]))
