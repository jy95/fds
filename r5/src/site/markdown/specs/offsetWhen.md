# offsetWhen 

<table>
  <thead>
    <tr>
      <th>Dosage</th>
      <th>Human readable text</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;offset&quot;: 1440
    }
  }
}
</code></pre></td>
      <td>1 day</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;offset&quot;: 60
    }
  }
}
</code></pre></td>
      <td>1 hour</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;offset&quot;: 15
    }
  }
}
</code></pre></td>
      <td>15 minutes</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;offset&quot;: 15,
      &quot;when&quot;: [&quot;MORN&quot;]
    }
  }
}
</code></pre></td>
      <td>15 minutes during the morning</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;when&quot;: [&quot;MORN&quot;, &quot;AFT&quot;]
    }
  }
}
</code></pre></td>
      <td>during the morning and during the afternoon</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;when&quot;: [&quot;MORN&quot;]
    }
  }
}
</code></pre></td>
      <td>during the morning</td>
    </tr>
  </tbody>
</table>
