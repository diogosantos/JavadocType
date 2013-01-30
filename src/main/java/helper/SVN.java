package helper;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: dsantos
 * Date: 1/29/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SVN {

    private SVNRepository repository;

    public SVN() throws SVNException {
        DAVRepositoryFactory.setup();
        repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(getRepositoryURL()));
        ISVNAuthenticationManager defaultAuthenticationManager = getUserAuthentication();
        repository.setAuthenticationManager(defaultAuthenticationManager);
    }

    private ISVNAuthenticationManager getUserAuthentication() {
        return SVNWCUtil.createDefaultAuthenticationManager("useranme", "password");
    }

    private String getRepositoryURL() {
        return "svn+ssh://xxxxx@xxxxxxxx.local/svn/xxxxxxxxxx/trunk";
    }


    public String getAuthor(File file) throws SVNException {
        Collection<SVNLogEntry> entries = repository.log(new String[]{getRelativePath(file)}, null, 0, -1, false, false);
        return getAuthorFromEntries(entries);
    }

    private String getRelativePath(File file) {
        String absolutePath = file.getPath();
        absolutePath = absolutePath.replace("C:\\Users\\dsantos\\Developer\\Intellij-IDEA\\ecaps", "/trunk");
        absolutePath = absolutePath.replace("\\", "/");
        return absolutePath;
    }

    private String getAuthorFromEntries(Collection<SVNLogEntry> entries) {
        sortEntriesByDate(entries);
        SVNLogEntry oldestEntry = entries.iterator().next();
        return oldestEntry.getAuthor();
    }

    private void sortEntriesByDate(Collection<SVNLogEntry> entries) {
        Collections.sort(new ArrayList<SVNLogEntry>(entries), new Comparator<SVNLogEntry>() {
            @Override
            public int compare(SVNLogEntry o1, SVNLogEntry o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }

}
