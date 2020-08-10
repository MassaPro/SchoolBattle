'use strict';

const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.addPlayersOfBlitz = functions.database.ref('/blitz-wait-list/{gameId}/{userId}').onCreate(async (change, context) => {
    const parentRef = change.ref.parent;
    const snapshot = await parentRef.once('value');
    const gameId = context.params.gameId;
    if (snapshot.numChildren() >= 2) {
      let childCount = 0;
      let firstName = "";
      let secondName = "";
      const updates = {};

      snapshot.forEach((child) => {
        ++childCount;
        if (childCount === 1) {
          firstName = child.key
        } else if (childCount === 2) {
          secondName = child.key
          let currentDate = Date.now() % 2;
          updates['Users/' + secondName + '/blitz/' + gameId + '/' + firstName] = currentDate;
          updates['Users/' + firstName + '/blitz/' + gameId + '/' + secondName] = 1 - currentDate;
          updates['blitz-wait-list/' + gameId + '/' + firstName] = null;
          updates['blitz-wait-list/' + gameId + '/' + secondName] = null;
          if (firstName > secondName) {
            let t = secondName;
            secondName = firstName;
            firstName = t;
          }
          updates['blitz/' + gameId + '/' +firstName + '_' + secondName + '/' + "true"] = ""
        }
      });
      return parentRef.parent.parent.update(updates);
    }
    return null;
  });

exports.clearTrashAndUpdateRatingBlitz = functions.database.ref('/blitz/{type}/{playersId}/winner').onCreate(async (change, context) => {
    const parentRef = change.ref.parent;
    const snapshot = await parentRef.child('true').once('value');
    const type = context.params.type;
    const playersId = context.params.playersId;
    const upd = {};
    upd[playersId] = null
    if (snapshot.val() === "") {
      return parentRef.parent.update(upd);
    }
    return parentRef.parent.update(upd);
  });

exports.clearTrashBlitz = functions.database.ref('/blitz/{type}/{playersId}/winner').onUpdate(async (change, context) => {
    const parentRef = change.after.ref.parent;
    const type = context.params.type;
    const playersId = context.params.playersId;
    const upd = {};
    upd[playersId] = null
    return parentRef.parent.update(upd);
  });






exports.addPlayersOfLong = functions.database.ref('/long-wait-list/{gameId}/{userId}').onCreate(async (change, context) => {
    const parentRef = change.ref.parent;
    const snapshot = await parentRef.once('value');
    const gameId = context.params.gameId;
    if (snapshot.numChildren() >= 2) {
      let childCount = 0;
      let firstName = "";
      let secondName = "";
      const updates = {};

      snapshot.forEach((child) => {
        ++childCount;
        if (childCount === 1) {
          firstName = child.key
        } else if (childCount === 2) {
          secondName = child.key
          let currentDate = Date.now()
          updates['Users/' + secondName + '/long/' + currentDate + '/' + gameId + '/' + firstName] = currentDate % 2;
          updates['Users/' + firstName + '/long/' + currentDate + '/' + gameId + '/' + secondName] = 1 - currentDate % 2;
          updates['long-wait-list/' + gameId + '/' + firstName] = null;
          updates['long-wait-list/' + gameId + '/' + secondName] = null;
          if (firstName > secondName) {
            let t = secondName;
            secondName = firstName;
            firstName = t;
          }
          updates['long/' + gameId + '/' + firstName + '_' + secondName + currentDate + '/' + "true"] = ""
        }
      });
      return parentRef.parent.parent.update(updates);
    }
    return null;
  });

exports.clearTrashAndUpdateRatingLong = functions.database.ref('/long/{type}/{playersId}/winner').onCreate(async (change, context) => {
    const parentRef = change.ref.parent;
    const snapshot = await parentRef.child('true').once('value');
    const type = context.params.type;
    const playersId = context.params.playersId;
    const upd = {};
    upd[playersId] = null
    if (snapshot.val() === "") {
      return parentRef.parent.update(upd);
    }
    return parentRef.parent.update(upd);
  });

exports.clearTrashLong = functions.database.ref('/long/{type}/{playersId}/winner').onUpdate(async (change, context) => {
    const parentRef = change.after.ref.parent;
    const type = context.params.type;
    const playersId = context.params.playersId;
    const upd = {};
    upd[playersId] = null
    return parentRef.parent.update(upd);
  });


//firebase deploy --only functions:clearBlitzGames