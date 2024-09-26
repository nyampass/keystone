(ns keystone.presentation.scenes.main
  (:require [integrant.core :as ig]
            [shadow.cljs.modern :refer [defclass]]
            [keystone.presentation.scenes.base :refer [Base]]))

(defclass Main
  (extends Base)

  (field usecase)
  (field setup)
  (field tilemaps)

  (constructor [this usecase tilemaps]
               (prn :constructor)
               (super #js {:key "main"})
               (set! (.-usecase this) usecase)
               (set! (.-setup this)
                     (fn [this]
                       (set! (.-tilemaps this) (map (fn [{:keys [name width height]}] (gen-tilemap name width height)) tilemaps))
                       (prn :done))))

  Object
  (create [this]
          (prn :create)
          (.disable-cursor this)
          (setup this)))


(defmethod ig/init-key :presentation.scenes/main [_ {:keys [usecase tilemaps]}]
  (Main. usecase tilemaps))

;; import Phaser from "phaser";
;; import { Direction, Input, MessageEventKey } from "../../infra/adapter/input";
;; import { randomNPCs } from "../entities/npc";
;; import { Player } from "../entities/player";
;; import * as entities from "../entities";
;; import { Message } from "../components/message";
;; import { Printer } from "../components/printer";
;; import { Editor } from "../components/editor";
;; import { IStorylineUsecase } from "../../domain/usecase/istoryline";
;; import { IScriptUsecase } from "../../domain/usecase/iscript";

;; export class Main extends Phaser.Scene {
;;   _input!: Input;
;;   _player!: Player;
;;   hitting: number | undefined;
;;   hit: number | undefined;
;;   touching: string | undefined;
;;   touch: string | undefined;
;;   moving: number[] | undefined;

;;   create() {
;;     const map = this.make.tilemap({
;;       key: "episode1",
;;       tileWidth: 64,
;;       tileHeight: 64,
;;     });
;;     const tilesets = [
;;       map.addTilesetImage("the_japan_collection_overgrown_backstreets")!,
;;       map.addTilesetImage("spritesheet_32x32")!,
;;     ];
;;     map.createLayer("ground", tilesets)?.setScale(1.5);

;;     const { stones } = entities.generator(this, map.objects[0]);

;;     const npcs = randomNPCs(this);
;;     this._player = new Player(this);
;;     stones?.forEach((stone) => {
;;       console.log(stone);
;;       this.physics.add.collider(this._player.sprite, stone.sprite, () => {
;;         if (
;;           !editor.isShown &&
;;           this.hitting !== stone.id &&
;;           this.hit !== stone.id &&
;;           !this.moving?.includes(stone.id)
;;         ) {
;;           editor.show();
;;           editor.focus();
;;           const code = this.script.load({
;;             episodeID: 1,
;;             id: stone.id,
;;           });
;;           editor.set(code ? code : "");
;;           const c = editor.get().split("\n");
;;           editor.cursor(c.length, c[c.length - 1].length + 1);
;;           this._player.idle();
;;           this.hitting = stone.id;
;;         }
;;       });
;;     });

;;     npcs.forEach((npc) => {
;;       //Test
;;       this.storylineUsecase.start(npc.id);
;;       this._player.collider(npc, () => {
;;         if (!editor.isShown && this.touching !== npc.id && this.touch !== npc.id) {
;;           this._player.idle();
;;           this.touching = npc.id;
;;           if (this.storylineUsecase.get(npc.id) == 0) {
;;             console.log("Nice to meet you!");
;;             this.storylineUsecase.proceed(npc.id);
;;           } else console.log("How have you been?");
;;         }
;;         //console.log("touched")
;;       });
;;     });
;;     map.createLayer("overlay", tilesets)?.setScale(1.5);

;;     const message = new Message(this);
;;     const printer = new Printer(this);
;;     const editor = new Editor();

;;     // this._player.colliderLayer(this, blocks!);

;;     const worldBounds = this.physics.world.bounds;
;;     worldBounds.width = map.widthInPixels * 1.5;
;;     worldBounds.height = map.heightInPixels * 1.5;

;;     this._input = new Input(this, this.storylineUsecase);
;;     this._input.setMoveEventHandler((direction) => {
;;       // if (this.storylineUsecase.currentEvent() !== undefined) {
;;       //   return;
;;       // }
;;       switch (direction) {
;;         case Direction.cancel:
;;           this._player.idle();
;;           break;

;;         default:
;;           this._player.move(direction);
;;           this.hit = this.hitting;
;;           this.hitting = undefined;
;;           this.touch = this.touching;
;;           this.touching = undefined;
;;           break;
;;       }
;;     });

;;     this._input.setMessageEventHandler((type) => {
;;       console.info([message.textLastIndex, message.textLastIndex]);
;;       console.info(["setMessageEventHandler", type]);
;;       switch (type) {
;;         case MessageEventKey.Next:
;;           if (message.hasNext()) {
;;             message.next();
;;           } else {
;;             console.info("progressStoryline");
;;             this.storylineUsecase.proceed("a");
;;           }
;;           break;
;;         case MessageEventKey.Prev:
;;           if (message.hasPrev()) {
;;             message.prev();
;;           }
;;       }
;;     });

;;     editor.init();
;;     editor.hide();
;;     editor.onEscape = () => {
;;       this.script.save(
;;         {
;;           episodeID: 1,
;;           id: stones[this.hitting! - 1].id,
;;         },
;;         editor.get(),
;;       );
;;     };
;;     editor.onRuntime = async () => {
;;       this.script.save(
;;         {
;;           episodeID: 1,
;;           id: stones[this.hitting! - 1].id,
;;         },
;;         editor.get(),
;;       );
;;       const target = stones[this.hitting! - 1];
;;       const res = await this.script.run({
;;         episodeID: 1,
;;         id: stones[this.hitting! - 1].id,
;;       });
;;       // console.log(res)
;;       editor.hide();
;;       for (const l of res) {
;;         console.log(l);
;;         const e = l as { op: string; args: string[] | number[] };
;;         switch (e.op) {
;;           case "move":
;;             if (!this.moving?.length) this.moving = [target.id];
;;             else this.moving?.push(target.id);
;;             if (e.args[0] == "forward" || e.args[0] == "back") {
;;               e.args[0] = (
;;                 e.args[0] == "forward"
;;                   ? ["up", "right", "down", "left"]
;;                   : ["down", "left", "up", "right"]
;;               )[target.param.dir];
;;             }
;;             if (e.args[0] == "left" || e.args[0] == "right") {
;;               const collider = this.physics.add.collider(this._player.sprite, target.sprite, () => {
;;                 this._player.sprite.x +=
;;                   target.sprite.body.touching.right && e.args[0] === "left"
;;                     ? 5
;;                     : target.sprite.body.touching.left && e.args[0] === "right"
;;                       ? -5
;;                       : 0;
;;               });

;;               const to = target.sprite.x + (e.args[0] == "left" ? -60 : 60);
;;               const tween = this.tweens.add({
;;                 targets: target.sprite,
;;                 x: to,
;;                 duration: 500,
;;                 onUpdate: () => {
;;                   target.sprite.body.x = target.sprite.x;
;;                 },
;;               });
;;               npcs.forEach((npc) => {
;;                 this.physics.add.collider(target.sprite, npc.sprite, () => {
;;                   if (target.sprite.body.touching.right || target.sprite.body.touching.left) {
;;                     tween.destroy();
;;                     target.sprite.body.x = target.sprite.x;
;;                   }
;;                 });
;;               });
;;               stones.forEach((stone) => {
;;                 if (target !== stone) {
;;                   this.physics.add.collider(target.sprite, stone.sprite, () => {
;;                     if (target.sprite.body.touching.right || target.sprite.body.touching.left) {
;;                       tween.destroy();
;;                       target.sprite.body.x = target.sprite.x;
;;                     }
;;                   });
;;                 }
;;               });
;;               await new Promise((resolve) => setTimeout(resolve, 550));
;;               target.sprite.body.x = to;
;;               collider.destroy();
;;             } else if (e.args[0] == "down" || e.args[0] == "up") {
;;               const collider = this.physics.add.collider(this._player.sprite, target.sprite, () => {
;;                 this._player.sprite.y +=
;;                   target.sprite.body.touching.up && e.args[0] === "down"
;;                     ? 5
;;                     : target.sprite.body.touching.down && e.args[0] === "up"
;;                       ? -5
;;                       : 0;
;;               });
;;               const to = target.sprite.y + (e.args[0] == "down" ? 60 : -60);
;;               const tween = this.tweens.add({
;;                 targets: target.sprite,
;;                 y: to,
;;                 duration: 500,
;;                 onUpdate: () => {
;;                   target.sprite.body.y = target.sprite.y;
;;                 },
;;               });
;;               npcs.forEach((npc) => {
;;                 this.physics.add.collider(target.sprite, npc.sprite, () => {
;;                   if (target.sprite.body.touching.up || target.sprite.body.touching.down) {
;;                     tween.destroy();
;;                     target.sprite.body.y = target.sprite.y;
;;                   }
;;                 });
;;               });
;;               stones.forEach((stone) => {
;;                 if (target !== stone) {
;;                   this.physics.add.collider(target.sprite, stone.sprite, () => {
;;                     if (target.sprite.body.touching.right || target.sprite.body.touching.left) {
;;                       tween.destroy();
;;                       target.sprite.body.y = target.sprite.y;
;;                     }
;;                   });
;;                 }
;;               });
;;               await new Promise((resolve) => setTimeout(resolve, 550));
;;               target.sprite.body.y = to;
;;               collider.destroy();
;;             }
;;             this.moving?.splice(this.moving.indexOf(target.id), 1);
;;             break;
;;           case "turn": {
;;             if (!this.moving?.length) this.moving = [target.id];
;;             else this.moving?.push(target.id);
;;             const rot = e.args[0] == "right" ? 1 : -1;
;;             const tween = this.tweens.add({
;;               targets: target.sprite,
;;               rotation: target.sprite.rotation + (Math.PI / 180) * 90 * rot,
;;               duration: 500,
;;             });
;;             const angle = target.param.dir + rot;
;;             target.param.dir = angle == -1 ? 3 : angle == 4 ? 0 : angle;
;;             await new Promise((resolve) => setTimeout(resolve, 550));
;;             tween.destroy();
;;             this.moving?.splice(this.moving.indexOf(target.id), 1);
;;             break;
;;           }
;;           case "print":
;;             if (!this.moving?.length) this.moving = [target.id];
;;             else this.moving?.push(target.id);
;;             printer.show(e.args[0].toString(), {
;;               isBlackScreen: false,
;;             });
;;             await new Promise((resolve) => setTimeout(resolve, 550));
;;             this.moving?.splice(this.moving.indexOf(target.id), 1);
;;             break;
;;           default:
;;             console.log("Error Occurred...");
;;             break;
;;         }
;;       }
;;     };
;;     // editor.init(this.sys.game.canvas.width,this.sys.game.canvas.height)
;;   }

;;   update(): void {
;;     this._player.update();
;;     this._input.update();
;;     // if (
;;     //   860 < this._player.sprite.x &&
;;     //   this._player.sprite.x < 910 &&
;;     //   740 < this._player.sprite.y &&
;;     //   this._player.sprite.y < 790
;;     // ) {
;;     //   this.scene.start("main", {});
;;     // }
;;   }

;;   init(data: any) {
;;     console.log(this.storylineUsecase.get("main"));
;;     console.log("Switched Scene : " + data["id"]);
;;   }
;; }
