import { GithubLeaderboardPage } from './app.po';

describe('github-leaderboard App', function() {
  let page: GithubLeaderboardPage;

  beforeEach(() => {
    page = new GithubLeaderboardPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
