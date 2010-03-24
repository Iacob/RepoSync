package luoyong.toolbox.reposync.lib;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class PathHandler extends CommonHandler {

   private RepoHandler repoHandler = null;

   protected RepoHandler getRepoHandler() {
      if (repoHandler == null) {
         repoHandler = new RepoHandler();
         return repoHandler;
      }else {
         return repoHandler;
      }
   }
}
