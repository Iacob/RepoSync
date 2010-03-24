package luoyong.toolbox.reposync.lib;

/**
 *
 * @author yong
 */
public class CommonHandler {

   private RepoSyncEventListener repoSyncEventListener = null;

   public RepoSyncEventListener getRepoSyncEventListener() {
      return repoSyncEventListener;
   }

   public void setRepoSyncEventListener(
           RepoSyncEventListener repoSyncEventListener) {
      this.repoSyncEventListener = repoSyncEventListener;
   }

   private void triggerEvent(String eventType, String sourceFile,
           String destFile, String action, String message) {

      if (this.getRepoSyncEventListener() != null) {
         RepoSyncEvent event = new RepoSyncEvent(eventType, sourceFile,
                 destFile, action, message);
         this.getRepoSyncEventListener().handleRepoSyncEvent(event);
      }
   }

   protected void triggerInfoEvent(String sourceFile, String destFile,
           String action, String message) {

      this.triggerEvent(RepoSyncEvent.EVENT_INFO, sourceFile, destFile, action,
              message);
   }

   protected void triggerSevereEvent(String sourceFile, String destFile,
           String action, String message) {

      this.triggerEvent(RepoSyncEvent.EVENT_SEVERE, sourceFile, destFile,
              action, message);
   }

   protected void triggerWarningEvent(String sourceFile, String destFile,
           String action, String message) {

      this.triggerEvent(RepoSyncEvent.EVENT_WARNING, sourceFile, destFile,
              action, message);
   }
}
