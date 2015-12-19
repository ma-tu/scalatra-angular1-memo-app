appModule.factory('noteModelService',[function(){
  var NOT_SELECTED_ROW = -1;

  var noteList = [];
  var selectedRow = NOT_SELECTED_ROW;

  return {
    setNoteList : function(notes) {
      noteList = notes;
    },

    getNoteList : function() {
      return noteList;
    },

    createAddNote : function(newId, newTitle) {
      return {
        account: "",
        id: newId,
        title: newTitle,
        note: "",
        updated_on : null
      }
    },

    addNote : function(note) {
      noteList.unshift(note);
      this.setNotSelected();
    },

    updateSelectedNoteTitle : function(title) {
      var selectedNote = this.getSelectedNote();
      if (selectedNote != null){
        selectedNote.title = title;
        this.moveTopOrder(selectedRow);
      }
    },

    updateSelectedNoteText : function(text) {
      var selectedNote = this.getSelectedNote();
      if (selectedNote != null){
        selectedNote.note = text;
        this.moveTopOrder(selectedRow);
      }
    },

    deleteSelectedNote : function() {
      if (this.hasSelectedNote()){
        noteList.splice(selectedRow, 1);
        this.setNotSelected();
      }
    },

    getSelectedNote : function(){
      if (this.hasSelectedNote()){
        return noteList[selectedRow];
      }else{
        return null;
      }
    },

    getSelectedNoteId : function() {
      if (this.hasSelectedNote() == false){
        return "";
      }else{
        return this.getSelectedNote().id;
      }
    },

    getSelectedNoteText : function() {
      if (this.hasSelectedNote() == false){
        return "";
      }else{
        return this.getSelectedNote().note;
      }
    },

    getSelectedNoteTitle : function() {
      if (this.hasSelectedNote() == false){
        return "";
      }else{
        return this.getSelectedNote().title;
      }
    },

    setSelectedRow : function(rowIndex) {
      selectedRow = rowIndex;
    },

    setNotSelected : function() {
      selectedRow = NOT_SELECTED_ROW;
    },

    hasSelectedNote : function() {
      return (selectedRow != NOT_SELECTED_ROW);
    },

    isSelectedRow : function(rowIndex) {
      return (selectedRow == rowIndex);
    },

    moveTopOrder : function(fromPos) {
      var targetNote = noteList.splice(fromPos, 1);
      noteList.unshift(targetNote[0]);
      selectedRow = 0;
    }
  };
}]);
