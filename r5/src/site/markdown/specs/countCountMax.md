# countCountMax 

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
      &quot;countMax&quot;: 3
    }
  }
}
</code></pre></td>
      <td>take 0 to 3 times</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;count&quot;: 1,
      &quot;countMax&quot;: 3
    }
  }
}
</code></pre></td>
      <td>take 1 to 3 times</td>
    </tr>
    <tr>
      <td><pre><code class="language-json">{
  &quot;timing&quot;: {
    &quot;repeat&quot;: {
      &quot;count&quot;: 1
    }
  }
}
</code></pre></td>
      <td>take 1 time</td>
    </tr>
  </tbody>
</table>
